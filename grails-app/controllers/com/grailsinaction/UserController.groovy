package com.grailsinaction


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def search() {}

    def results(String loginId) {
        def users = User.where { loginId =~ "%${loginId}%" }.list()
        return [users     : users,
                term      : params.loginId,
                totalUsers: User.count()]
    }

    def advSearch() {}

    def advResults() {
        def profileProps = Profile.metaClass.properties*.name
        def profiles = Profile.withCriteria {
            "${params.queryType}" {

                params.each { field, value ->

                    if (profileProps.grep(field) && value) {
                        ilike(field, "%${value}%")
                    }
                }

            }

        }
        [profiles: profiles]

    }

/*
    def update() {
        // Attaches existing user from session scope
        def user = session.user?.attach()
        if (user) {
            // Updates fields based on matching params
            user.properties = params
            // Validates user is still current
            if (user.save()) {

                flash.message = "Successfully updated user"

            } else {
                flash.message = "Failed to update user"
            }
            [ user : user ]

        } else {
            response.sendError(404)
        }
    }
*/

    def register() {
        if (request.method == "POST") {
            def user = new User(params)
            if (user.validate()) {
                user.save()
                flash.message = "Successfully Created User"
                redirect(uri: '/')
            } else {
                flash.message = "Error Registering User"
                return [user: user]
            }
        }
    }

    def profile(String id) {
        def user = User.findByLoginId(id)
        if (user) {
            return [profile: user.profile]
        } else {
            response.sendError(404)
        }
    }


    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model: [userInstanceCount: User.count()]
    }

    def show(User userInstance) {
        respond userInstance
    }

    def create() {
        respond new User(params)
    }

    @Transactional
    def save(User userInstance) {
        if (userInstance == null) {
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view: 'create'
            return
        }

        userInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
                redirect userInstance
            }
            '*' { respond userInstance, [status: CREATED] }
        }
    }

    def edit(User userInstance) {
        respond userInstance
    }

    @Transactional
    def update(User userInstance) {
        if (userInstance == null) {
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view: 'edit'
            return
        }

        userInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'User.label', default: 'User'), userInstance.id])
                redirect userInstance
            }
            '*' { respond userInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(User userInstance) {

        if (userInstance == null) {
            notFound()
            return
        }

        userInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'User.label', default: 'User'), userInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

class UserRegistrationCommand {
    String loginId
    String password
    String passwordRepeat
    byte[] photo
    String fullName
    String bio
    String homepage
    String email
    String timezone
    String country
    String jabberAddress

    static constraints = {
        importFrom Profile
        importFrom User
        password(size: 6..8, blank: false,
                validator: { passwd, urc ->
                    return passwd != urc.loginId
                })
        passwordRepeat(nullable: false,
                validator: { passwd2, urc ->
                    return passwd2 == urc.password
                })
    }
}