package com.grailsinaction

import org.springframework.web.multipart.commons.CommonsMultipartFile

class PhotoUploadCommand {
    byte[] photo
    String loginId
}

class ImageController {

    /**
     * Save image to database
     * @param puc
     * @return
     */
    def upload(PhotoUploadCommand puc) {
        def user = User.findByLoginId(puc.loginId)
        user.profile.photo = puc.photo
        user.save(failOnError: true, flush: true)
        redirect controller: "user", action: "profile", id: puc.loginId
    }

    def form() {
        // pass through to upload form
        [ userList : User.list() ]
    }

    def renderImage(String id) {
        def user = User.findByLoginId(id)
        if (user?.profile?.photo) {
            response.setContentLength(user.profile.photo.size())
            response.outputStream.write(user.profile.photo)
        } else {
            response.sendError(404)
        }
    }

    def renderRawImage(String id) {
        def user = User.findByLoginId(id)
        if (user?.profile?.photo) {
            response.setContentLength(user.profile.photo.size())
            response.outputStream.write(user.profile.photo)
        } else {
            response.sendError(404)
        }
    }

    /**
     * Save image to file
     * @param puc
     * @return
     */
    def rawUpload(PhotoUploadCommand puc) {
        def user = User.findByLoginId(puc.loginId)

        // a Spring MultipartFile
        CommonsMultipartFile mpf = request.getFile('photo')
        def webRootDir = servletContext.getRealPath("/")
        def userDirInProject = "resources/myUsers/${params.loginId}"
        def userDir = new File(webRootDir, userDirInProject)
        if (!userDir.exists()) {
            userDir.mkdirs()
        }
        File myFile = new File( userDir, mpf.originalFilename)
        user.profile.pathToPhoto = "${userDirInProject}/${mpf.originalFilename}"
                // Save image to database
                user.profile.photo = puc.photo
        user.save(failOnError: true, flush: true)
        if (!mpf?.empty && mpf.size < 1024*300) { //Ensures file size is less than 200 KB
            mpf.transferTo(myFile)
        }
        redirect controller: "user", action: "profile", id: puc.loginId
    }
    
}
