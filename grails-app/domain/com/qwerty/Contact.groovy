package com.qwerty

class Contact {

    String name
    String email

    Tenant tenant

    static constraints = {
        name(nullable: false)
        email(nullable: false)
        tenant(nullable: false)
    }
}
