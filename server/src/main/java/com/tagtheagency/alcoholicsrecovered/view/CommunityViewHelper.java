package com.tagtheagency.alcoholicsrecovered.view;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import com.tagtheagency.alcoholicsrecovered.model.ForumMessage;
import com.tagtheagency.alcoholicsrecovered.model.ForumThread;

public class CommunityViewHelper {

	public CommunityViewHelper() {
		
	}
	
	public ForumMessage getMostRecentMessage(ForumThread thread) {
		return thread.getForumMessages().get(thread.getMessageCount() - 1);
	}
	
	public String showBody(ForumMessage message) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(message.getBody());
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
	}
}
