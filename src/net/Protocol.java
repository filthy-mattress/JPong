package net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import util.Util;

public class Protocol {
	public static final char INITIATOR = '!';
	public static final char SEPARATOR = ':';
	public static final String REQ_PREFIX = INITIATOR+"REQ";
	public static final String RES_PREFIX = INITIATOR+"RES";
	public static final String KEY_PREFIX = INITIATOR+"KEY";
	public static final String SET_PREFIX = INITIATOR+"SET";
	public static final String GO_UP_PROP = "GO_UP";
	public static final String GO_DOWN_PROP = "GO_DOWN";
	public static final String LISTEN_PROP = "LISTEN";
	public static final int FAILURE = 0;
	public static final int SUCCESS = 1;
	public static final int REQ_KEYSTATE = 2;
	public static final int REQ_KEYMODE = 3;
	public static final String[] BOOL_PROPS = {
		GO_UP_PROP,
		GO_DOWN_PROP,
		LISTEN_PROP
	};
	
	private static final char escapechar = '\\';
	private static final HashMap<Character,String> escapeSeqs = Util.zip(
			new Character[]{'"','\'','n','t','\\'},
			"\";';\n;\t;\\".split(";"));
	
	/**
	 * <p>Tokenizes messages separating on SEPARATOR with quoting and escaping capabilities.</p>
	 * 
	 * <p>Examples (where SEPARATOR==':')</p>
	 * 	<table>
	 * 		<tr>
	 * 			<th>Message</th>
	 * 			<th>Tokens</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td><pre class="code">!SET:GO_UP:false</pre></td>
	 * 			<td>
	 * 				<ol>
	 * 					<li><code>!SET</code></li>
	 * 					<li><code>GO_UP</code></li>
	 * 					<li><code>false</code></li>
	 * 				</ol>
	 * 			</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td><pre class="code">!SET:NAME:"Injection attack: avoidance"</pre></td>
	 * 			<td>
	 * 				<ol>
	 * 					<li><code>!SET</code></li>
	 * 					<li><code>NAME</code></li>
	 * 					<li><code>Injection attack: avoidance</code></li>
	 * 				</ol>
	 * 			</td>
	 * 		</tr>
	 * 	<code></code>
	 * 
	 * 	</table>
	 * @param message Message to tokenize
	 * @return Array of tokens
	 */
	public static String[] tokenize(String message){
		ArrayList<String> res = new ArrayList<String>();
		String buffer = "";
		boolean quoted = false, escape=false, done=false;
		
		for(char c : message.toCharArray()){
			if(escape){
				String s = escapeSeqs.get(c);
				if(s!=null && (!done))
					buffer+=s;
				escape=false;
			}else if(c==escapechar){
				escape=true;
			}else if(c=='"'){
				if(quoted){
					done=true;
				}else if(buffer.isEmpty()){
					quoted=true;
				}
			}else if(c==SEPARATOR){
				if((!quoted) || done){
					res.add(buffer);
					buffer="";
					quoted=false;
					done=false;
				}else if(quoted && (!done)){
					buffer+=c;
				}
			}else{
				if(!done){
					buffer+=c;
				}
			}
		}
		res.add(buffer);
		return res.toArray(new String[res.size()]);
	}

	private Protocol() {}
	
	public static void main(String[] args){
		String[] messages = {
				"!SET:GO_UP:false",
				"!SET:NAME:\"Injection attack: avoidance\"",
				"!SET:NAME:\"Injection attack: \\\"avoidance\\\"\"",
				"!SET:NAME:\"Injection attack: avoidance\"invisible!",
				"!SET:NAME:c-c-c-combo-breaker!\"Injection attack: avoidance\"invisible!"
		};
		for(String s : messages){
			System.out.println(s+"\t-->\t"+Arrays.toString(tokenize(s)));
		}
	}

}
