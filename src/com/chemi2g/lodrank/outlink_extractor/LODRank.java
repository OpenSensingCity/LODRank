/**
 * 
 */
package com.chemi2g.lodrank.outlink_extractor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.jena.atlas.web.HttpException;
import org.apache.jena.riot.RiotException;

/**
 * @author chemi2g
 *
 */
public class LODRank {

	HashSet<String>			processedDatasets;
	int						numDatasets;
	long					numTriples;

	OutlinkConfiguration	conf;
	OutlinkExtractor		outlinkExtractor;
	Date					date	= new Date();

	private LODRank(String[] args) {
		conf = OutlinkConfiguration.newInstance(args);
	}

	public static void main(String[] args) {
		LODRank lodrank = new LODRank(args);
		lodrank.readPartialProcessing(OutlinkConfiguration.DEFAULT_CONFIG_FOLDER);
		lodrank.run(OutlinkConfiguration.DEFAULT_CONFIG_FOLDER);
	}

	void readPartialProcessing(String path) {
		BufferedReader reader = null;
		String line;
		// File fileDatasets = new File(path + "/" + PROCESSED_DATASETS_FILE);
		try {
			try {
				// Read number of triples
				reader = new BufferedReader(new FileReader(path + "/" + OutlinkConfiguration.PROCESSED_TRIPLES_FILE));
				numTriples = Long.parseLong(reader.readLine());
				reader.close();
			} catch (NumberFormatException e) {
				numTriples = 0;
				numDatasets = 0;
				System.err.println("Error reading number of triples processed. Starting count again.");
			}
			// Read already processed datasets
			processedDatasets = new HashSet<>();
			reader = new BufferedReader(new FileReader(path + "/" + OutlinkConfiguration.PROCESSED_DATASETS_FILE));
			while ((line = reader.readLine()) != null) {
				processedDatasets.add(line);
				numDatasets++;
			}
			System.out.println("Partial status retrieved. Datasets processed: " + numDatasets + ". Triples processed: " + numTriples);
			System.out.println("Resuming process...");
		} catch (FileNotFoundException e) {
			System.err.println("No files to retrieve partial processing status. Starting again.");
			processedDatasets = new HashSet<>();
			numTriples = 0;
			numDatasets = 0;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error while retrieven partial processing status. Starting again.");
			processedDatasets = new HashSet<>();
			numTriples = 0;
			numDatasets = 0;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	void run(String path) {
		outlinkExtractor = new OutlinkExtractor(conf.processSubjects(), conf.processPredicates(), conf.processObjects(), numTriples);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter processedDatasetsWriter;
		String line;
		int numLine = 0;
		if (processedDatasets == null) {
			processedDatasets = new HashSet<>();
		}

		try {
			processedDatasetsWriter = new BufferedWriter(new FileWriter(path + "/" + OutlinkConfiguration.PROCESSED_DATASETS_FILE, true));

			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					FileWriter numTriplesWriter = null;
					try {
						numTriplesWriter = new FileWriter(path + "/" + OutlinkConfiguration.PROCESSED_TRIPLES_FILE);
						numTriplesWriter.write(Long.toString(outlinkExtractor.getNumTriples()) + "\n");
						numTriplesWriter.flush();
						processedDatasetsWriter.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							numTriplesWriter.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});

			while ((line = reader.readLine()) != null) {
				if (((++numLine) - conf.getStart()) % conf.getStep() == 0) {
					String[] urls = line.split(" ");
					if (!processedDatasets.contains(urls[1])) {
						Entry<String, Set<String>> outlinks;
						try {
							if ((outlinks = outlinkExtractor.processDataset(urls[1], urls[0])) != null) {
								Thread t = new Thread(new OutlinkWriter(outlinks.getKey(), outlinks.getValue()));
								t.start();
								processedDatasetsWriter.write(urls[1] + "\n");
							}
						} catch (FileNotFoundException e) {
							System.err.println(new Timestamp(date.getTime()) + " No cleaned file found for dataset " + urls[1]);
							System.err.println(new Timestamp(date.getTime()) + " Resuming the process...");
						} catch (RiotException e) {
							System.err.println(new Timestamp(date.getTime()) + " Error with Jena Parser while processing dataset " + urls[1]);
							System.err.println(new Timestamp(date.getTime()) + " Resuming the process...");
						} catch (IOException e) {
							System.err.println(new Timestamp(date.getTime()) + " IOException while processing dataset " + urls[1]);
							e.printStackTrace();
							System.err.println(new Timestamp(date.getTime()) + " Resuming the process...");
						} catch (HttpException e) {
							System.err.println(new Timestamp(date.getTime()) + " HttpException while processing dataset " + urls[1]);
							e.printStackTrace();
							System.err.println(new Timestamp(date.getTime()) + " Resuming the process...");
						}
					} else {
						System.out.println(new Timestamp(date.getTime()) + urls[1] + " already processed. Skipping.");
					}
				}
				// else {
				// System.out.println("Dataset " + numLine + " ignored");
				// }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
