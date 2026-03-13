package com.example.demo.service;

import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
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
    public Document create(Document doc) {

        Document saved = repository.save(doc);

//        ObjectIdentity oi =
//                new ObjectIdentityImpl(Document.class, saved.getId());
//
//        MutableAcl acl = aclService.createAcl(oi);
//
//        Authentication auth =
//                SecurityContextHolder.getContext().getAuthentication();
//
////        Sid sid = new PrincipalSid(auth);
//
//        String username = SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getName();
//
//        acl.insertAce(
//                acl.getEntries().size(),
//                BasePermission.ADMINISTRATION,
//                new PrincipalSid(username),
//                true
//        );
//
//
//
//        acl.setOwner(new PrincipalSid(username));
//        aclService.updateAcl(acl);

        return saved;
    }
}
