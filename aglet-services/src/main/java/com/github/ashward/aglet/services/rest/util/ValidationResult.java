package com.github.ashward.aglet.services.rest.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationResult {
	enum FailType {
		ERROR, WARNING
	}

	class ValidationMessage {
		private final FailType type;
		private final String messageID;

		public ValidationMessage(FailType type, String messageID) {
			super();
			this.type = type;
			this.messageID = messageID;
		}

		public FailType getType() {
			return type;
		}

		public String getMessageID() {
			return messageID;
		}
	}

	private static Map<String, List<ValidationMessage>> messages;

	public ValidationResult() {
		messages = new HashMap<String, List<ValidationMessage>>();
	}

	public Map<String, List<ValidationMessage>> getMessages() {
		return messages;
	}
}
