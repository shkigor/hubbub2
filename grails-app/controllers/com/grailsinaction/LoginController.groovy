package com.grailsinaction

class LoginController {

    def form(String id) {
        return [user: new LoginCommand(impersonateId: id)]
    }

//    def signIn() {
//        if (params.impersonateId) {
//            def user = User.findByLoginId(params.impersonateId)
//            if (user && user.password == params.password) {
//                session.user = user
//                redirect(controller: 'post', action: 'timeline', id: user.loginId)
//            } else {
//                flash.message = "User is not exist!"
//                redirect(action: 'form')
//            }
//        }
//    }

//    def signIn(String impersonateId, String password) {
//            def user = User.findByLoginId(impersonateId)
//            if (user && user.password == password) {
//                session.user = user
//                redirect(controller: 'post', action: 'timeline', id: user.loginId)
//            } else {
//                flash.message = "User is not exist!"
//                redirect(action: 'form')
//            }
//    }

    def signIn(LoginCommand command) {
        if (request.method == 'POST') {
            if (command.hasErrors()) {
                render view: 'form', model: [user: command]
                return
            }

            def user = User.findByLoginId(command.impersonateId)
            if (user && user.password == command.password) {
                session.user = user
                redirect(controller: 'post', action: 'timeline', id: user.loginId)
            } else {
                flash.message = 'User is not exist!'
                redirect(action: 'form', id: command.impersonateId)
//                redirect(action: 'form', params: params)
            }
        } else {
            response.sendError(404)
        }
    }
}

class LoginCommand {
    String impersonateId
    String password

    static constraints = {
        impersonateId blank: false
        password size: 3..8, blank: false
    }
}
