# Spring Security ACL Practice Project

## Document Sharing System

## 1. Overview

Build a **simple Document Sharing API** using **Spring Boot and Spring
Security ACL**.

The goal of this project is to understand **object-level security**
where permissions are applied **per document**, not just per role.

Example:

  User      Document   Permission
  --------- ---------- ------------
  Alice     Doc1       OWNER
  Bob       Doc1       READ
  Charlie   Doc1       WRITE

In this system: - A user can create a document - The document owner can
share it with other users - Shared users have specific permissions

------------------------------------------------------------------------

# 2. Functional Requirements

## 2.1 User Management

The system contains predefined users:

-   Alice
-   Bob
-   Charlie

Authentication can be simple (in-memory users).

------------------------------------------------------------------------

## 2.2 Document Management

Each document contains:

-   id
-   title
-   content
-   owner

Users can:

### Create document

POST /documents

Request

{ "title": "My Document", "content": "Hello ACL" }

Behavior:

-   Document is saved
-   Creator becomes OWNER
-   ACL entry is created automatically

------------------------------------------------------------------------

### Read document

GET /documents/{id}

Access rule:

User must have READ permission.

Example:

-   Owner -\> allowed
-   Shared READ user -\> allowed
-   Other users -\> denied

------------------------------------------------------------------------

### Update document

PUT /documents/{id}

Access rule:

User must have WRITE permission.

------------------------------------------------------------------------

### Delete document

DELETE /documents/{id}

Access rule:

User must have DELETE permission.

------------------------------------------------------------------------

# 3. Document Sharing

Owner can share a document with another user.

POST /documents/{id}/share

Request:

{ "username": "bob", "permission": "READ" }

Possible permissions:

-   READ
-   WRITE
-   DELETE

Behavior:

-   Add new ACL entry for the target user
-   Permission applies only to that document

------------------------------------------------------------------------

# 4. Security Rules

Use Spring Security ACL to enforce permissions.

Example:

@PreAuthorize("hasPermission(#id, 'Document', 'READ')")

@PreAuthorize("hasPermission(#id, 'Document', 'WRITE')")

@PreAuthorize("hasPermission(#id, 'Document', 'DELETE')")

------------------------------------------------------------------------

# 5. Database Tables

The project must include the default Spring ACL tables:

-   acl_sid
-   acl_class
-   acl_object_identity
-   acl_entry

These tables store:

-   who the user is
-   what object they access
-   which permission they have

------------------------------------------------------------------------

# 6. Expected Behavior Example

Scenario:

1.  Alice creates Doc1
2.  Alice shares Doc1 with Bob (READ)
3.  Alice shares Doc1 with Charlie (WRITE)

Expected results:

  Action                 Result
  ---------------------- ---------
  Alice reads Doc1       Allowed
  Bob reads Doc1         Allowed
  Bob edits Doc1         Denied
  Charlie edits Doc1     Allowed
  Charlie deletes Doc1   Denied

------------------------------------------------------------------------

# 7. Non‑Functional Requirements

Technology stack:

-   Java
-   Spring Boot
-   Spring Security
-   Spring Security ACL
-   Spring Data JPA
-   H2 Database

------------------------------------------------------------------------

# 8. Bonus Challenges (Optional)

If the base project works, try implementing:

### 1. Document list filtering

GET /documents

Return only documents the user has permission to READ.

------------------------------------------------------------------------

### 2. Add SHARE permission

Only users with SHARE permission can share documents.

------------------------------------------------------------------------

### 3. Folder hierarchy

Folder -\> Documents

Allow permissions to inherit from folders.

------------------------------------------------------------------------

# 9. Learning Goals

After completing this project you should understand:

-   Object-level security
-   Spring Security ACL architecture
-   Permission masks
-   ACL database model
-   Permission evaluation flow

------------------------------------------------------------------------

# End of Requirement
