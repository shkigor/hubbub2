package com.grailsinaction

class RegisterController {

    static allowedMethods = [index: "GET", register: "POST"]

    def index() { }

    def register(UserRegistrationCommand urc) {
            if (urc.hasErrors()) {
                render view: "index", model: [user: urc]
            } else {
                def user = new User(urc.properties)
                user.profile = new Profile(urc.properties)
                if (user.validate() && user.save()) {
                    flash.message = "Welcome aboard, ${urc.fullName ?: urc.loginId}"
                    redirect(uri: '/')
                } else {
                    // maybe not unique loginId?
                    render view: "/register/index", model: [user: urc]
                }
            }
    }
}
