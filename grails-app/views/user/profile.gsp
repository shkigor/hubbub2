<html>
<head>
    <title>Profile for ${profile.fullName}</title>
    <meta name="layout" content="main"/>
    <style>
    .profilePic {
        border: 1px dotted gray;
        background: lightyellow;
        padding: 1em;
        font-size: 1em;
    }
    </style>
</head>
<body>

    <div class="profilePic">
        <g:if test="${profile.photo}">
            <h2>Image from Database</h2>
            <img src="${createLink(controller: 'image', action: 'renderImage', id: profile.user.loginId)}"/>
        </g:if>
        <h2>Image from file</h2>
        <img src="${resource(file: "$profile.pathToPhoto")}"/>
        <p>Profile for <strong>${profile.fullName}</strong></p>
        <p>Bio: ${profile.bio}</p>
    </div>

</body>
</html>
