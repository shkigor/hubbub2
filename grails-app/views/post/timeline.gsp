<html>
    <head>
        <title>
            Timeline for ${ user.profile ? user.profile.fullName : user.loginId }
        </title> 
        <meta name="layout" content="main"/>
        <asset:stylesheet src="chosen.css"/>
        %{--<asset:javascript src="chosen.jquery.js"/>--}%
    </head>
    <body>
        <h1>Timeline for ${ user.profile ? user.profile.fullName : user.loginId }</h1>

    <g:select name="user.age" from="${18..65}" value="${it}"
              noSelection="['':'-Choose your age-']"/>

    <g:select id="type" name='type.id'
              noSelection="${['null':'Select One...']}"
              from='${com.grailsinaction.User.list()}'
              optionKey="loginId" optionValue="dateCreated"></g:select>

    <g:select name='selectAjax'
              noSelection="${['null':'Select One...']}"
              from=''
              optionKey=""
              optionValue=""
              onclick="getUserLoginIdAndEmail()">
    </g:select>

    <div>
        <em>Into This</em>
        <select id="chosenSelect" data-placeholder="Choose a Country..." class="chosen-select" style="width:350px;" tabindex="2">
            <option value=""></option>
            <option value="United States">United States</option>
            <option value="United Kingdom">United Kingdom</option>
            <option value="Afghanistan">Afghanistan</option>
            <option value="Aland Islands">Aland Islands</option>
            <option value="Albania">Albania</option>
            %{--<option value="Algeria">Algeria</option>--}%
            %{--<option value="American Samoa">American Samoa</option>--}%
            %{--<option value="Andorra">Andorra</option>--}%
            %{--<option value="Angola">Angola</option>--}%
            %{--<option value="Anguilla">Anguilla</option>--}%
            %{--<option value="Antarctica">Antarctica</option>--}%
            %{--<option value="Antigua and Barbuda">Antigua and Barbuda</option>--}%
        </select>
    </div>

        <g:if test="${flash.message}">
            <div class="flash">
                ${flash.message}
            </div>
        </g:if>
        
        <div id="newPost">
            <h3>
                What is ${user.profile.fullName} hacking on right now?
            </h3>
            <p>
                <g:form action="addPost" id="${params.id}">
                    <g:textArea id='postContent' name="content"
                         rows="3" cols="50"/><br/>
                    <g:submitButton name="post" value="Post"/>
                </g:form>
            </p>
        </div>
        
        <div id="allPosts">
            <g:each in="${user.posts}" var="post">
                <div class="postEntry">
                    <div class="postText">
                        ${post.content}
                    </div>
                    <div class="postDate">
                        ${post.dateCreated}
                    </div>
                </div>
            </g:each>
        </div>

<g:javascript>

// $("#chosenSelect").click(function(){
// console.log('AAA');
//         $('#chosenSelect').empty(); //remove all child nodes
//         var newOption = $('<option value="1">test</option>');
//         $('#chosenSelect').append(newOption);
//         $('#chosenSelect').trigger("chosen:updated");
//     });


%{--$('.chzn-choices input').autocomplete({--}%
  %{--source: function( request, response ) {--}%
    %{--$.ajax({--}%
      %{--url: "${createLink(controller: 'post', action: 'ajaxGetUserLoginIdAndEmail')}",--}%
      %{--dataType: "json",--}%
      %{--beforeSend: function(){$('ul.chzn-results').empty();},--}%
      %{--success: function( data ) {--}%
        %{--console.log('QQQ');--}%
        %{--response( $.map( data, function( item ) {--}%
          %{--$('ul.chzn-results').append('<li class="active-result">' + item.email + '</li>');--}%
        %{--}));--}%
      %{--}--}%
    %{--});--}%
  %{--}--}%
%{--});--}%

$('#chosenSelect').on('chosen:showing_dropdown', function(evt, params) {
    // do_something(evt, params);
    getUserLoginIdAndEmail();
  });

function getUserLoginIdAndEmail() {
    $.ajax({
        cache: false,
        url: "${createLink(controller: 'post', action: 'ajaxGetUserLoginIdAndEmail')}",
        type: 'GET',
        data: {contractId: 12},
        dataType: 'json',
        success: function(data) {
        console.log(data);
        var $select = $('#chosenSelect');
        // $select.find('option').remove();
        $select.empty();
        var key = -1;
        var value = 'Choose a Country...';
        $select.append('<option value=' + key + '>' + value + '</option>');
        $.each(data,function(index, value)
            {
                $select.append('<option value=' + value.loginId + '>' + value.email + '</option>');
            });
        $select.trigger("chosen:updated");
        // $select.trigger("chosen:change");
        },
        error: function() {
            alert('Server error.');
        }
    });
}

    var config = {
      '.chosen-select'           : {disable_search: true},
      '.chosen-select-deselect'  : {allow_single_deselect:true},
      '.chosen-select-no-single' : {disable_search_threshold:10},
      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chosen-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
</g:javascript>

    </body>
</html>

