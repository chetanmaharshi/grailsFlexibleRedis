package com.qwerty

class Tenant implements Serializable {
    String orgId //this is the webaddress
    String tagLine
    String name
    String defaultClassName
    String logoUrl
    String companyUrl
    Long accountOwner

    static hasMany = [apiKeys: TenantKeys]

    static mapping = {
        apiKeys lazy: false
    }

    static constraints = {
    }
}
