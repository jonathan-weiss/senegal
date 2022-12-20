rootProject.name = "senegal"

include("senegal-code-generator")
include("senegal-code-generator:senegal-engine")
include("senegal-code-generator:senegal-plugin-api")
include("senegal-code-generator:senegal-example-plugins")

// example project
include("senegal-example-app")
include("senegal-example-app:app")
include("senegal-example-app:app-hsqldb-server")
include("senegal-example-app:shared-domain")
include("senegal-example-app:domain")
include("senegal-example-app:persistence")
include("senegal-example-app:frontend-api")
include("senegal-example-app:frontend")
include("senegal-example-app:open-api")
include("senegal-example-app:customized-code-generator")

