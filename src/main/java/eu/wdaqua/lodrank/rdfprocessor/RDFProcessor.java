/**
 *
 */
package eu.wdaqua.lodrank.rdfprocessor;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

import com.sun.org.apache.xpath.internal.SourceTree;
import fr.opensensingcity.urlstructurevectorgeneration.Link.Link;
import fr.opensensingcity.urlstructurevectorgeneration.Link.LinkFactory;
import fr.opensensingcity.urlstructurevectorgeneration.Link.LinkLibrary;
import fr.opensensingcity.urlstructurevectorgeneration.Link.Types;
import org.apache.jena.graph.Triple;
import org.apache.jena.riot.Lang;
import org.apache.jena.sparql.core.Quad;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.wdaqua.lodrank.Source.Format;
import eu.wdaqua.lodrank.exception.DestinationNotOpenableException;
import eu.wdaqua.lodrank.exception.InvalidResourceException;
import eu.wdaqua.lodrank.exception.SourceNotOpenableException;
import eu.wdaqua.lodrank.linkextractor.LinkExtractor;
import eu.wdaqua.lodrank.linkwriter.LinkWriter;
import eu.wdaqua.lodrank.loader.RDFLoader;

/**
 * @author José M. Giménez-García
 *
 */
public class RDFProcessor {

	protected RDFLoader<?>	loader;
	protected LinkExtractor	linkExtractor;
	protected LinkWriter	linkWriter;

	protected Logger		logger;

	public RDFProcessor() {
		this.logger = LogManager.getLogger(getClass());
	}

	public void setRDFLoader(final RDFLoader<?> loader) {
		this.loader = loader;
	}

	public void setLinkExtractor(final LinkExtractor linkExtractor) {
		this.linkExtractor = linkExtractor;
	}

	public void setLinkWriter(final LinkWriter linkWriter) {
		this.linkWriter = linkWriter;
	}

	public void setDataset(final URL dataset) {
		this.linkExtractor.setDataset(dataset);
	}

	public void run(final Lang lang) throws IOException {
		run(Format.getFormat(lang));
	}

	public void run(final Format format) throws IOException {
		switch (format) {
			case QUADS:
				runQuads();
				break;
			case TRIPLES:
				runTriples();
		}
	}

	public void runTriples() throws SourceNotOpenableException, DestinationNotOpenableException {
		this.linkWriter.open();
		this.loader.open();
		try {
			if (this.linkExtractor.getDataset() != null) {
				this.loader.forEachRemaining(triple -> {
					try {
						this.logger.debug("Getting links for triple: " + triple);
						this.logger.debug("Dataset: " + this.linkExtractor.getDataset());
						this.linkExtractor.setTriple((Triple) triple);
						final Entry<String, Collection<String>> entry = this.linkExtractor.getLinks();
						if (entry != null) {
							this.linkWriter.addLinks(entry.getKey(), entry.getValue());
						}
					} catch (final InvalidResourceException e) {
						this.logger.warn("Invalid resource when reading Triple [" + ((Triple) triple).toString() + "]");
					}
				});
			} else {
				this.logger.warn("Could not obtain dataset for source " + this.loader.getSource().toString() + ". No links will be extracted.");
			}
		} catch (final Throwable t) {
			this.logger.info("Catching throwable in the loop", t);
		}
		try {
			this.loader.close();
			this.linkWriter.printLinks();
			this.linkWriter.close();
		} catch (final Throwable t) {
			this.logger.info("Catching throwable after the loop", t);
		}
	}

	// public void runQuads() throws SourceNotOpenableException, DestinationNotOpenableException {
	// this.logger.debug("Extracting links from quads.");
	// this.linkWriter.open();
	// this.loader.open();
	// this.loader.forEachRemaining(quad -> {
	// try {
	// this.logger.debug("Getting links for quad: " + ((Quad) quad).toString());
	// this.linkExtractor.setQuad((Quad) quad);
	// final Entry<String, Collection<String>> entry = this.linkExtractor.getLinks();
	// if (entry != null) {
	// this.linkWriter.addLinks(entry.getKey(), entry.getValue());
	// }
	// } catch (final InvalidResourceException e) {
	// this.logger.debug("Invalid resource when reading Quad " + ((Quad) quad).toString());
	// }
	// });
	// this.loader.close();
	// this.linkWriter.printLinks();
	// this.linkWriter.close();
	// }

	public void runQuads() throws SourceNotOpenableException, DestinationNotOpenableException {
		this.logger.debug("Extracting links from quads.");
		this.linkWriter.open();
		this.loader.open();

		long counterRDFType = 0;
		int numTriples = 0;
		Map<String, List<Link>> linkTable = new HashMap<String, List<Link>>();

		try {
			while (this.loader.hasNext()) {
				final Quad quad = (Quad) this.loader.next();
				try {
					this.logger.debug("Getting links for quad: " + quad.toString());
					this.linkExtractor.setQuad(quad);

					final Entry<String, Collection<String>> entry = this.linkExtractor.getLinks();
					if (entry != null) {
						this.linkWriter.addLinks(entry.getKey(), entry.getValue());
						counterRDFType++;

						Collection<String> links = entry.getValue();


						String subjectIRI = links.iterator().next();
						if (subjectIRI != null){

						}

						String predicateIRI = links.iterator().next();
						if (predicateIRI != null){

						}

						String objectIRI = links.iterator().next();
						if (objectIRI != null){

						}
					}
				} catch (final InvalidResourceException e) {
					this.logger.debug("Invalid resource when reading Quad " + quad.toString());
				}
				System.out.println();
				numTriples++;
				Runtime.getRuntime().exec("echo "+numTriples+" > /home/bakerally/Downloads/testlinks/counter");
				/*if (numTriples++ > 10000){
					LinkLibrary.serialize("/home/bakerally/Downloads/testlinks/");

					System.out.println();
					System.out.println("###########################Triples##################");
					for (String key:linkTable.keySet()){
						System.out.println(key+ " "+linkTable.get(key).size());
					}
					break;
				}*/

			}
			LinkLibrary.serialize("/home/bakerally/Downloads/testlinks/");
			System.out.println("RDF Type Triples:"+counterRDFType);
		} catch (final Throwable t) {
			this.logger.info("Catching throwable in the loop", t);
		}

		try {
//			this.logger.info("1");
			this.loader.close();
//			this.logger.info("2");
			this.linkWriter.printLinks();
//			this.logger.info("3");
			this.linkWriter.close();
//			this.logger.info("4");
		} catch (final Throwable t) {
			this.logger.info("Catching throwable after the loop", t);
		}
	}

	Map<String, List<Link>> addToMap(Map<String, List<Link>> linkTable,Link link ){
		if (!linkTable.containsKey(link.toString())){
			List <Link> currentLinkTable = new ArrayList<>();
			currentLinkTable.add(link);
			linkTable.put(link.toString(),currentLinkTable);
		} else {
			List <Link> currentLinkTable = linkTable.get(link.toString());
			currentLinkTable.add(link);
			linkTable.put(link.toString(),currentLinkTable);
		}
		return linkTable;
	}

}
