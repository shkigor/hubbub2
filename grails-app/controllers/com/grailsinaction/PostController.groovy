package com.grailsinaction

import com.grailsinaction.models.UserEmailModel
import grails.converters.JSON

class PostController {
    static scaffold = true

    static defaultAction = "home"

    def postService

    def home() {
        if (!params.id) {
            params.id = "chuck_norris"
        }
        redirect(action: 'timeline', params: params)
    }
    
    def timeline(String id) {
        def user = User.findByLoginId(id)
        if (!user) {
            response.sendError(404)
        } else {
            [ user : user ]
        }
    }

    def addPost(String id, String content)  {
        try {
            def newPost = postService.createPost(id, content)
            flash.message = "Added new post: ${newPost.content}"
        } catch (PostException pe) {
            flash.message = pe.message
        }
        redirect(action: 'timeline', id: id)
    }

/*
    def addPost(@RequestParameter('frm_id') String id,
                @RequestParameter('frm_content') String content) {
        // your logic here...
    }
*/

    def personal() {
        if (!session.user) {
            redirect controller: "login", action: "form"
            return
        } else {
            // Need to reattach the user domain object to the session using the refresh() method.
            render view: "timeline", model: [ user : session.user.refresh() ]
        }
    }

    def ajaxGetUserLoginIdAndEmail() {
        ArrayList<UserEmailModel> list = new ArrayList<>()
        User.list().each { user ->
            list << new UserEmailModel(loginId: user.loginId, email: user.profile.email)
        }
        render list as JSON
    }
}
