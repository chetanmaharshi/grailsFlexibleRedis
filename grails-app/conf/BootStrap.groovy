import com.qwerty.Contact
import com.qwerty.Tenant
import com.qwerty.TenantKeys

class BootStrap {

    def init = { servletContext ->

        if(Tenant.count()==0){
            Tenant tenant = new Tenant(orgId: "1", tagLine: "Java", name: "Chetan", defaultClassName: "Chetan", logoUrl: "www.chetan.com", companyUrl: "www.chetan.com", accountOwner: 1)
            tenant.save(flush: true)
            Tenant tenant1 = new Tenant(orgId: "2", tagLine: "Angular", name: "Deepak", defaultClassName: "deepak", logoUrl: "www.deepak.com", companyUrl: "www.deepak.com", accountOwner: 2)
            tenant.save(flush: true)

            TenantKeys tenantKeys = new TenantKeys(accessKey1: "key1_1", accessKey2: "key1_2").save(flush: true)
            TenantKeys tenantKeys2 = new TenantKeys(accessKey1: "key2_1", accessKey2: "key2_2").save(flush: true)

            tenant.addToApiKeys(tenantKeys)
            tenant.save(flush: true)
            tenant1.addToApiKeys(tenantKeys2)
            tenant1.save(flush: true)

            Contact contact = new Contact(name: "chetan", email: "chetan.com", tenant: tenant).save(flush: true)
            contact = new Contact(name: "deepak", email: "deepak.com", tenant: tenant).save(flush: true)

        }

    }
    def destroy = {
    }
}
