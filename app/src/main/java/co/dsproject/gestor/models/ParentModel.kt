package co.dsproject.gestor.models

data class ParentModel (
        val title : String = "",
        val children : List<TaskModel>
)