rootProject.name = "senegal-example-app"

// example project
include("app")
include("app-hsqldb-server")
include("shared-domain")
include("domain")
include("persistence")
include("frontend-api")
include("frontend")
include("sourceamazing-customizing")


includeBuild("../sourceamazing-code-generator")

