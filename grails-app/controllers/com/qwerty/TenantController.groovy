package com.qwerty



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TenantController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def tenantService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tenantService.fetchTenantList(params), model:[tenantInstanceCount: Tenant.count()]
    }

    def show() {
        respond tenantService.showTenant(params)
    }

    def create() {
        /*
        * Fired Queries for this action is below
        * Hibernate: select this_.id as id1_1_0_, this_.version as version2_1_0_, this_.access_key1 as access_k3_1_0_,
         * this_.access_key2 as access_k4_1_0_ from tenant_keys this_
        * */
        respond new Tenant(params)
    }

    @Transactional
    def save() {

        Tenant tenantInstance = new Tenant()
        bindData(tenantInstance,params,['id','apiKeys'])

        tenantInstance = tenantService.saveTenant(tenantInstance,params)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tenant.label', default: 'Tenant'), tenantInstance.id])
                redirect tenantInstance
            }
            '*' { respond tenantInstance, [status: CREATED] }
        }
    }

    def edit() {
        log.debug "Params ${params}"
        respond tenantService.editTenant(params)
    }

    @Transactional
    def update() {
        log.debug "Params ${params}"
        Tenant tenantInstance = tenantService.editTenant(params)
        log.debug "After getting Tenant from cache tenantInstance ${tenantInstance}"

        bindData(tenantInstance,params,['id','apiKeys'])

        log.debug "After Bindig Data Tenant"

        tenantInstance = tenantService.updateTenant(tenantInstance,params)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Tenant.label', default: 'Tenant'), tenantInstance.id])
                redirect tenantInstance
            }
            '*'{ respond tenantInstance, [status: OK] }
        }
    }

    @Transactional
    def delete() {
        tenantService.deleteTenant(params)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Tenant.label', default: 'Tenant'), params.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tenant.label', default: 'Tenant'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
