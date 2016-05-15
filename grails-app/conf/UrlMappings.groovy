class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/timeline/chuck_norris" {
            controller = "post"
            action = "timeline"
            id = "chuck_norris"
        }

        "/timeline" {
            controller = "post"
            action = "personal"
        }

        "/users/$id" {
            controller = "post"
            action = "timeline"
        }

        "/users/$loginId/feed/$format?" {
            controller = "post"
            action = "feed"
            constraints {
                format(inList: ['rss', 'atom'])
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
