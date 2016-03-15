package com.jlsoft.framework.aop;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.context.annotation.ScopedProxyMode;

public class ScopeResolver implements ScopeMetadataResolver {

    public ScopeMetadata resolveScopeMetadata(BeanDefinition bd) {
        ScopeMetadata scopeMetadata = new ScopeMetadata();
        scopeMetadata.setScopedProxyMode(ScopedProxyMode.NO);
        scopeMetadata.setScopeName("prototype");
        return scopeMetadata;
    }
}
