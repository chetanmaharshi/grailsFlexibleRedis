package com.qwerty


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TenantKeysController {

    def tenantService
    def redisFlexibleCacheService


    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tenantService.fetchTenantKeysList(params), model: [tenantKeysInstanceCount: TenantKeys.count()]
    }

    def show() {
        respond tenantService.showTenantKeys(params)
    }

    def create() {
        respond new TenantKeys(params)
    }

    @Transactional
    def save(TenantKeys tenantKeysInstance) {
        if (tenantKeysInstance == null) {
            notFound()
            return
        }

        if (tenantKeysInstance.hasErrors()) {
            respond tenantKeysInstance.errors, view: 'create'
            return
        }

        tenantService.saveTenantKeys(tenantKeysInstance)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tenantKeys.label', default: 'TenantKeys'), tenantKeysInstance.id])
                redirect tenantKeysInstance
            }
            '*' { respond tenantKeysInstance, [status: CREATED] }
        }
    }

    def edit() {
        respond tenantService.showTenantKeys(params)
    }

    @Transactional
    def update() {
        TenantKeys tenantKeysInstance = TenantKeys.findById(params?.id as Long)
        int oldVersion = tenantKeysInstance.version
        if (tenantKeysInstance == null) {
            notFound()
            return
        }
        bindData(tenantKeysInstance,params,["id"])
        if (tenantKeysInstance.hasErrors()) {
            respond tenantKeysInstance.errors, view: 'create'
            return
        }
        tenantKeysInstance.save flush: true
        int newVersion = tenantKeysInstance.version
        log.debug " newVersion $newVersion and $oldVersion"
        if (newVersion != oldVersion) {
            List<Tenant> tenants = Tenant.withCriteria() {
                apiKeys {
                    'in'('id', [tenantKeysInstance.id])
                }
            }
            if(tenants){
                tenants?.each{ Tenant tenant ->
                    redisFlexibleCacheService.evictCache("Tenant:"+tenant.id,{
                        log.debug "Evicted ${"Tenant:"+tenant.id}"
                    })
                }
            }
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'TenantKeys.label', default: 'TenantKeys'), tenantKeysInstance.id])
                redirect tenantKeysInstance
            }
            '*' { respond tenantKeysInstance, [status: OK] }
        }
    }

    @Transactional
    def delete() {
        tenantService.deleteTenantKeys(params)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'TenantKeys.label', default: 'TenantKeys'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tenantKeys.label', default: 'TenantKeys'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
