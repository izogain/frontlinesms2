databaseChangeLog = {
	changeSet(author: "frontlinesms (generated)", id: "1345215027861-1") {
		addNotNullConstraint(columnDataType: "boolean", columnName: "RECEIVE", tableName: "SMSLIB_FCONNECTION")
	}

	changeSet(author: "frontlinesms (generated)", id: "1345215027861-2") {
		addNotNullConstraint(columnDataType: "boolean", columnName: "SEND", tableName: "SMSLIB_FCONNECTION")
	}

}

