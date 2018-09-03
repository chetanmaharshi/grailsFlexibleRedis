
package com.qwerty

import grails.plugin.cache.Cacheable
import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class TenantService {

    def redisFlexibleCacheService
    def customRedisFlexibleCacheService
    def dataSource

    List<Tenant> fetchTenantList(Map params){
        List<Tenant> tenants = customRedisFlexibleCacheService.doCacheList(Tenant, "Tenant:list", "TenantList", 300, false , {
            List<Long> ids = Tenant.createCriteria().list{
                projections{
                    property('id')
                }
            }
            return ids
        })
        return tenants
    }

    Tenant showTenant(Map params){
        Tenant tenant = redisFlexibleCacheService.doCache('Tenant:'+params?.id, 'TenantGroup', 300, false, {
            return Tenant.findById(params?.id as Long)
        })

        // Example that we can use Catched object to another query

        List<Contact> contactList = Contact.findAllByTenant(tenant)
        println "contactList::${contactList}"

        return tenant
    }

    Tenant saveTenant(Tenant tenant ,Map params){
        log.debug "params ${params}"
        tenant = tenant.save(flush:true)
        /*
        * Fired Queries is
        * Hibernate: insert into tenant (version, account_owner, company_url, default_class_name, logo_url, name, org_id, tag_line) values (?, ?, ?, ?, ?, ?, ?, ?)
        * */
        List<TenantKeys> tenantKeys = []
        if(params?.apiKeys instanceof String){
            tenantKeys.add(TenantKeys.findById(params?.apiKeys as Long))
            /*
        * Fired Queries is
        * Hibernate: select this_.id as id1_1_0_, this_.version as version2_1_0_, this_.access_key1 as access_k3_1_0_,
        * this_.access_key2 as access_k4_1_0_ from tenant_keys this_ where this_.id=? limit ?
        * */
        }else{
            params?.apiKeys?.each{ itt ->
                tenantKeys.add(TenantKeys.findById(itt as Long))
                /*
        * Fired Queries is
        * Hibernate: select this_.id as id1_1_0_, this_.version as version2_1_0_, this_.access_key1 as access_k3_1_0_,
        * this_.access_key2 as access_k4_1_0_ from tenant_keys this_ where this_.id=? limit ?
        * */
            }
        }
        tenant.apiKeys = tenantKeys
        tenant =  tenant.save(flush:true)
        /*
        * Fired Queries
        * Hibernate: update tenant set version=?, account_owner=?, company_url=?, default_class_name=?, logo_url=?, name=?,
        *  org_id=?, tag_line=? where id=? and version=?
            Hibernate: insert into tenant_tenant_keys (tenant_api_keys_id, tenant_keys_id) values (?, ?)
        * */

        //NOTE for our customized function at the time of add operation , he has to pass  key and group name of List
         tenant = customRedisFlexibleCacheService.doCachePost(Tenant, "Tenant:list", "TenantList", 300, false , {
            return tenant
        })
        return tenant
    }

    Tenant editTenant(Map params){
        Tenant tenant = redisFlexibleCacheService.doCache('Tenant:'+params?.id, 'TenantGroup', 300, false, {
            println "Inside closure to get editTenant get"
            return Tenant.findById(params?.id as Long)
        })

        return tenant
    }

    Tenant updateTenant(Tenant tenantInstance, Map params){
        log.debug "params in service ${params}"
        tenantInstance.apiKeys = []
        tenantInstance =  tenantInstance.save(flush:true)

        //upto above code two queries will fired by grail gorm
        /*
        Hibernate: update tenant set version=?, account_owner=?, company_url=?, default_class_name=?, logo_url=?, name=?, org_id=?, tag_line=? where id=? and version=?
        Hibernate: delete from tenant_tenant_keys where tenant_api_keys_id=?
        * */

        List<TenantKeys> tenantKeys = []
        if(params?.apiKeys instanceof String){
            tenantKeys.add(TenantKeys.findById(params?.apiKeys as Long))
        }else{
            params?.apiKeys?.each{ itt ->
                tenantKeys.add(TenantKeys.findById(itt as Long))
            }
        }
        tenantInstance.apiKeys = tenantKeys
        tenantInstance =  tenantInstance.save(flush:true)
        //69 to 78 line below queries will fired by GORM
        /*
        * Hibernate: select this_.id as id1_1_0_, this_.version as version2_1_0_, this_.access_key1 as access_k3_1_0_, this_.access_key2 as access_k4_1_0_ from tenant_keys this_ where this_.id=? limit ?
          Hibernate: update tenant set version=?, account_owner=?, company_url=?, default_class_name=?, logo_url=?, name=?, org_id=?, tag_line=? where id=? and version=?
          Hibernate: insert into tenant_tenant_keys (tenant_api_keys_id, tenant_keys_id) values (?, ?)
        *
        * */

        tenantInstance = customRedisFlexibleCacheService.doCachePut("Tenant:${tenantInstance.id}", "TenantGroup", 300, false , {
            return tenantInstance
        })
        return tenantInstance
    }

    void deleteTenant(Map params){
        Tenant tenant = redisFlexibleCacheService.doCache('Tenant:'+params?.id, 'TenantGroup', 300, false, {
            return Tenant.findById(params?.id as Long)
        })
        tenant.delete flush:true
        /*
        * Fired Queries on line 97
        *
        * Hibernate: delete from tenant_tenant_keys where tenant_api_keys_id=?
          Hibernate: delete from tenant where id=? and version=?
        * */
        customRedisFlexibleCacheService.doCacheDelete(Tenant, "Tenant:list", "TenantList", 300, false , {
            return params.id as Long
        })
    }


    List<TenantKeys> fetchTenantKeysList(Map params){
        return TenantKeys.list(params)
    }

    TenantKeys showTenantKeys(Map params){
        return TenantKeys.findById(params?.id as Long)
    }

    TenantKeys saveTenantKeys(TenantKeys tenantInstance){
        return tenantInstance.save(flush:true)
    }

    TenantKeys editTenantKeys(Map params){
        return TenantKeys.findById(params?.id as Long)
    }

    void deleteTenantKeys(Map params){
        TenantKeys tenantKeys = TenantKeys.findById(params?.id as Long)
        List<Tenant> tenants = Tenant.withCriteria() {
            apiKeys {
                'in'('id', [params?.id as Long])
            }
        }
        if(tenants){
            tenants?.each{ Tenant tenant ->
                tenant.removeFromApiKeys(tenantKeys)
                tenant.save(flush: true)
                redisFlexibleCacheService.evictCache("Tenant:"+tenant.id,{
                    log.debug "Evicted ${"Tenant:"+tenant.id}"
                })
            }
        }
        tenantKeys.delete flush:true
    }
}
