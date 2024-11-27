rootProject.name = "prog-distribuida"
include("ejemplo01")
include("ejemplo_rest")
include("ejemplo_rest:ejemplo_jpa")
findProject(":ejemplo_rest:ejemplo_jpa")?.name = "ejemplo_jpa"
include("ejemplo_jpa")
