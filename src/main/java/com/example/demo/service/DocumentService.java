package com.example.demo.service;

import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentService {
    private final DocumentRepository repository;
    private final JdbcMutableAclService aclService;

    public DocumentService(DocumentRepository repository,
                           JdbcMutableAclService aclService) {
        this.repository = repository;
        this.aclService = aclService;
    }

    @Transactional
    public Document get(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public Document create(Document doc) {

        Document saved = repository.save(doc);

        ObjectIdentity oi =
                new ObjectIdentityImpl(Document.class, saved.getId());

        MutableAcl acl = aclService.createAcl(oi);

//        Authentication auth =
//                SecurityContextHolder.getContext().getAuthentication();

//        Sid sid = new PrincipalSid(auth);

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Sid sid = new PrincipalSid(username);
        acl.setOwner(sid);

        // grant permissions
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, sid, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, sid, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, sid, true);

        aclService.updateAcl(acl);

        return saved;
    }

    @Transactional
    public void grantPermission(Long id, String username, String permission) {

        ObjectIdentity oi = new ObjectIdentityImpl(Document.class, id);

        MutableAcl acl = (MutableAcl) aclService.readAclById(oi);

        Sid sid = new PrincipalSid(username);

        Permission perm = mapPermission(permission);

        acl.insertAce(
                acl.getEntries().size(),
                perm,
                sid,
                true
        );

        aclService.updateAcl(acl);
    }

    private Permission mapPermission(String permission) {
        return switch (permission.toUpperCase()) {
            case "READ" -> BasePermission.READ;
            case "WRITE" -> BasePermission.WRITE;
            case "ADMIN" -> BasePermission.ADMINISTRATION;
            case "DELETE" -> BasePermission.DELETE;
            default -> throw new IllegalArgumentException("Invalid permission");
        };
    }
}
