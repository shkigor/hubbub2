package com.grailsinaction

class LameSecurityFilters {

    def filters = {
        all(controller:'*', action:'*') {
            before = {

            }
            after = { Map model ->

            }
            afterView = { Exception e ->
                log.debug "Finished running [controller - ${controllerName}] and [action - ${actionName}]"
            }
        }

        secureActions(controller:'post', action:'(addPost|deletePost)') {
            before = {
                if (params.impersonateId) {
                    session.user = User.findByLoginId(params.impersonateId)
                }

                if (!session.user) {
                    redirect(controller: 'login', action: 'form')
                    return false
                }
            }
            after = { model->
            }
            afterView = {
                log.debug "SecureActions filters - Finished running ${controllerName} - ${actionName}"
            }
        }
    }
}
