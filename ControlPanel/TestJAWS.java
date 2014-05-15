package ControlPanel;

import edu.smu.tspell.wordnet.*;
import java.lang.System;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TestJAWS
{
	public static void main(String[] args)
	{
		{
			String wordForm = "wine";
			 StringBuffer syns = new StringBuffer(); 
			WordNetDatabase database = WordNetDatabase.getFileInstance();
			Synset[] synsets = database.getSynsets(wordForm);
			Synset[] nsynsets = database.getSynsets(wordForm, SynsetType.NOUN); 
			Synset[] asynsets = database.getSynsets(wordForm, SynsetType.ADJECTIVE);
			//Synset[] asatsynsets = database.getSynsets(wordForm, SynsetType.ADJECTIVE_SATELLITE);
			//Synset[] advsynsets = database.getSynsets(wordForm, SynsetType.ADVERB);
			Synset[] vsynsets = database.getSynsets(wordForm, SynsetType.VERB);
            ArrayList<String> wordslist = new ArrayList<String>();
			if (synsets.length > 0)
			{
				NounSynset nsynsets1; 
				AdjectiveSynset asynsets1;
				//AdjectiveSatelliteSynset asatsynsets1;
				//AdverbSynset advsynsets1;
				VerbSynset vsynsets1;
				if (nsynsets.length > 0)
				{
					NounSynset[] hyponyms;
					NounSynset[] hypernyms;	
					NounSynset[] instancehyponyms;
					NounSynset[] instancehypernyms;
					NounSynset[] memberholonyms;
					NounSynset[] membermeronyms;
					NounSynset[] partmeronyms;
					NounSynset[] partholonyms;
					NounSynset[] substanceholonyms;
					NounSynset[] substancemeronyms;
					NounSynset[] topics;
					NounSynset[] fullhyponyms;
					
					for (int i = 0; i < synsets.length; i++)
					{
						String[] word=synsets[i].getWordForms();
						for(int jt=0;jt<word.length;jt++)
														{
						wordslist.add(word[jt].toLowerCase());
						System.out.println("Synset"+i+" "+" : "+word[jt]);
														}
					
					}
					
					for (int i = 0; i < nsynsets.length; i++)
					{
						nsynsets1=(NounSynset)(nsynsets[i]);
						
						//System.out.println();
						//System.out.println("Noun "+" "+nsynsets1);
						int count=0;
						hyponyms = nsynsets1.getHyponyms();
						String[] words= new String[100];
						if(hyponyms.length>0)
						{	
							//System.out.println();
							//System.out.println("Hyponyms :");
							for(int j=0;j<hyponyms.length;j++)
							{
								String[] word=hyponyms[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
								{
									wordslist.add(word[jt].toLowerCase());
								System.out.println("Hyponym"+j+" "+" : "+word[jt]);
								}
								
							}
							fullhyponyms = new NounSynset[100];
							for(int j=0;j<hyponyms.length;j++)
							{
								NounSynset temp =(NounSynset)(hyponyms[j]);
								NounSynset[] tempfullhyponyms= temp.getHyponyms();
								
								if(tempfullhyponyms.length>0)
								{
									System.out.println(tempfullhyponyms.length);
									System.out.println(tempfullhyponyms[0]);
								for(int jtemp=0;jtemp<tempfullhyponyms.length;jtemp++)
								{
									String[] word=tempfullhyponyms[jtemp].getWordForms();
									fullhyponyms[count]=tempfullhyponyms[jtemp];
									for(int jt=0;jt<word.length;jt++)
									{
											wordslist.add(word[jt].toLowerCase());
											System.out.println("FullHyponym"+j+" "+" : "+word[jt]);
									}
									//System.out.println("Full Hyponym"+jtemp+" "+" : "+fullhyponyms[count]);
									count++;

								}
								}
								
							}
						}
						
						hypernyms = nsynsets1.getHypernyms();
						if(hypernyms.length>0)
						{	
							System.out.println();
							System.out.println("Hypernyms :");
							for(int j=0;j<hypernyms.length;j++)
							{
								String[] word=hypernyms[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Hypernym"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Hypernym"+j+" "+" : "+hypernyms[j]);
								
							}
						}
						
						instancehyponyms = nsynsets1.getInstanceHyponyms();
						if(instancehyponyms.length>0)
						{	
							System.out.println();
							System.out.println("Instance Hyponyms :");
							for(int j=0;j<instancehyponyms.length;j++)
							{
								String[] word=instancehyponyms[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Instance Hyponym"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Instance Hyponym"+j+" "+" : "+instancehyponyms[j]);
								
							}
						}
						
						instancehypernyms = nsynsets1.getInstanceHypernyms();
						if(instancehypernyms.length>0)
						{	
							System.out.println();
							System.out.println("Instance Hypernyms :");
							for(int j=0;j<instancehypernyms.length;j++)
							{
								String[] word=instancehypernyms[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Instance Hypernym"+j+" "+" : "+word[jt]);
																}
								System.out.println("Instance Hypernym"+j+" "+" : "+instancehypernyms[j]);
								
							}
						}
						
						memberholonyms = nsynsets1.getMemberHolonyms();
						if(memberholonyms.length>0)
						{	
							System.out.println();
							System.out.println("Member Holonyms :");
							for(int j=0;j<memberholonyms.length;j++)
							{
								String[] word=memberholonyms[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Member Holonym"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Member Holonym"+j+" "+" : "+memberholonyms[j]);
								
							}
						}
						
						membermeronyms = nsynsets1.getMemberMeronyms();
						if(membermeronyms.length>0)
						{	
							System.out.println();
							System.out.println("Member Meronyms :");
							for(int j=0;j<membermeronyms.length;j++)
							{
								String[] word=membermeronyms[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("HMember Meronym"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Member Meronym"+j+" "+" : "+membermeronyms[j]);
								
							}
						}
						
						partmeronyms = nsynsets1.getPartMeronyms();
						if(partmeronyms.length>0)
						{	
							System.out.println();
							System.out.println("Part Meronyms :");
							for(int j=0;j<partmeronyms.length;j++)
							{
								String[] word=partmeronyms[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Part Meronym"+j+" "+" : "+word[jt]);
																}
								System.out.println("Part Meronym"+j+" "+" : "+partmeronyms[j]);
								
							}
						}
						
						partholonyms = nsynsets1.getPartHolonyms();
						if(partholonyms.length>0)
						{	
							System.out.println();
							System.out.println("Part Holonyms :");
							for(int j=0;j<partholonyms.length;j++)
							{
								String[] word=partholonyms[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Part Holonym"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Part Holonym"+j+" "+" : "+partholonyms[j]);
								
							}
						}
						
						substanceholonyms = nsynsets1.getSubstanceHolonyms();
						if(substanceholonyms.length>0)
						{	
							System.out.println();
							System.out.println("Substance Holonyms :");
							for(int j=0;j<substanceholonyms.length;j++)
							{
								String[] word=substanceholonyms[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Substance Holonym"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Substance Holonym"+j+" "+" : "+substanceholonyms[j]);
								
							}
						}
						
						substancemeronyms = nsynsets1.getSubstanceMeronyms();
						if(substancemeronyms.length>0)
						{	
							System.out.println();
							System.out.println("Substance Meronyms :");
							for(int j=0;j<substancemeronyms.length;j++)
							{
								String[] word=substancemeronyms[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Substance Meronym"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Substance Meronym"+j+" "+" : "+substancemeronyms[j]);
								
							}
						}
						
						topics = nsynsets1.getTopics();
						if(topics.length>0)
						{	
							System.out.println();
							System.out.println("Topics :");
							for(int j=0;j<topics.length;j++)
							{
								String[] word=topics[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Topic"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Topic"+j+" "+" : "+topics[j]);
								
							}
						}
					}
					}
				if (vsynsets.length > 0)
				{
					VerbSynset[] troponyms;
					VerbSynset[] hypernyms;	
					VerbSynset[] outcomes;
					VerbSynset[] verbgroup;
					
					for (int i = 0; i < vsynsets.length; i++)
					{
						vsynsets1=(VerbSynset)(vsynsets[i]);
						
						System.out.println();
						System.out.println("Verb "+" "+vsynsets1);
						
						troponyms = vsynsets1.getTroponyms();
						if(troponyms.length>0)
						{	
							System.out.println();
							System.out.println("Troponyms :");
							for(int j=0;j<troponyms.length;j++)
							{
								String[] word=troponyms[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Troponym"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Troponym"+j+" "+" : "+troponyms[j]);
								
							}
						}
						
						hypernyms = vsynsets1.getHypernyms();
						if(hypernyms.length>0)
						{	
							System.out.println();
							System.out.println("Hypernyms :");
							for(int j=0;j<hypernyms.length;j++)
							{
								String[] word=hypernyms[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Hypernym"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Hypernym"+j+" "+" : "+hypernyms[j]);
								
							}
						}
						
						outcomes = vsynsets1.getOutcomes();
						if(outcomes.length>0)
						{	
							System.out.println();
							System.out.println("Outcomes :");
							for(int j=0;j<outcomes.length;j++)
							{
								String[] word=outcomes[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Outcome"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Outcome"+j+" "+" : "+outcomes[j]);
								
							}
						}
						
						verbgroup = vsynsets1.getVerbGroup();
						if(verbgroup.length>0)
						{	
							System.out.println();
							System.out.println("Verb Group :");
							for(int j=0;j<verbgroup.length;j++)
							{
								String[] word=verbgroup[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Verb Group"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Verb Group"+j+" "+" : "+verbgroup[j]);
								
							}
						}
					}
					
				}
				
				if (asynsets.length > 0)
				{
					AdjectiveSynset[] related;
					AdjectiveSynset[] similar;	
					
					for (int i = 0; i < asynsets.length; i++)
					{
						asynsets1=(AdjectiveSynset)(asynsets[i]);
						
						System.out.println();
						System.out.println("Adjective "+" "+asynsets1);
						
						related = asynsets1.getRelated();
						if(related.length>0)
						{	
							System.out.println();
							System.out.println("Related :");
							for(int j=0;j<related.length;j++)
							{
								String[] word=related[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Related"+j+" "+" : "+word[jt]);
																}
								System.out.println("Related"+j+" "+" : "+related[j]);
								
							}
						}
						
						similar = asynsets1.getSimilar();
						if(similar.length>0)
						{	
							System.out.println();
							System.out.println("Similar :");
							for(int j=0;j<similar.length;j++)
							{
								String[] word=similar[j].getWordForms();
								for(int jt=0;jt<word.length;jt++)
																{
								wordslist.add(word[jt].toLowerCase());
								System.out.println("Similar"+j+" "+" : "+word[jt]);
																}
								//System.out.println("Similar"+j+" "+" : "+similar[j]);
								
							}
						
						}
						
					}
					//System.out.println(wordslist);
				}
				/*
				System.out.println("The following synsets contain '" +
						wordForm + "' or a possible base form " +
						"of that text:");
				for (int i = 0; i < synsets.length; i++)
				{
					System.out.println("");
					WordSense temp = new WordSense(wordForm,synsets[i]);
					Synset synsetsense = temp.getSynset();
					
					WordSense[] tr = synsets[i].getDerivationallyRelatedForms(wordForm); 
					for (int j = 0; j < tr.length; j++)
					{
						//syns.append(tr[j]);
						System.out.println("Derivationally Related words "+j+" : "+tr[j]);
					}
					//System.out.println("Printing synset of sense"+synsetsense);
					String[] wordForms = synsets[i].getWordForms();
					System.out.println();
					for (int j = 0; j < wordForms.length; j++)
					{
						syns.append(wordForms[j]);
						
						System.out.print((j > 0 ? ", " : "") +
								wordForms[j]);
					}
					syns.append(synsets[i].getDefinition());
					System.out.println(": " + synsets[i].getDefinition());
				}
				*/
				
				System.out.println(wordslist);
			}
			else
			{
				System.err.println("No synsets exist that contain " +
						"the word form '" + wordForm + "'");
			}
		
			String stemp=syns.toString();
			String[] syntemp=stemp.split("[ \n\t\r.,;:!?(){}]");
			List<String> synlist = Arrays.asList(syntemp);
			HashSet<String> sset = new HashSet<String>(synlist);
		
		}
		
	}

}