package org.smof.collection;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bson.BsonDocument;
import org.smof.element.Element;
import org.smof.parsers.SmofParser;

import com.mongodb.client.FindIterable;

@SuppressWarnings("javadoc")
public class SmofResults<T extends Element> {
	
	private final FindIterable<BsonDocument> rawResults;
	private final SmofParser parser;
	private final Class<T> elClass;

	SmofResults(FindIterable<BsonDocument> rawResults, SmofParser parser, Class<T> elementClass) {
		this.rawResults = rawResults;
		this.parser = parser;
		elClass = elementClass;
	}
	
	public Stream<T> stream() {
		return StreamSupport.stream(rawResults.spliterator(), false)
				.map(d -> parser.fromBson(d, elClass));
	}
	
	public List<T> asList() {
		return stream().collect(Collectors.toList());
	}
	
	
}