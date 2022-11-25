rootProject.name = "senegal"

include("senegal-engine")

// example project
include("senegal-example")
include("senegal-example:app")
include("senegal-example:app-hsqldb-server")
include("senegal-example:shared-domain")
include("senegal-example:domain")
include("senegal-example:persistence")
include("senegal-example:frontend-api")
include("senegal-example:frontend")
include("senegal-example:open-api")
