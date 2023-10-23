package com.eva.timemanagementapp.data.room

import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

object AppMigrations {

	@RenameColumn(
		tableName = "SESSION_INFO_TABLE",
		fromColumnName = "SESSION_OPTION",
		toColumnName = "SESSION_DURATION"
	)
	class RenameSessionDurationField : AutoMigrationSpec

	@DeleteColumn(
		tableName = "SESSION_INFO_TABLE",
		columnName = "ADDED_AT"
	)
	class DeleteSessionAtField : AutoMigrationSpec

}