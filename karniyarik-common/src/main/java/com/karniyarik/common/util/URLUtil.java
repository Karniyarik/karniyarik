package com.karniyarik.common.util;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

/**
 * Some URL related methods gathered as methods in this utility class.
 * 
 * $Id: URLUtil.java,v 1.13 2003/04/29 17:53:49 vanrogu Exp $
 * 
 */
public class URLUtil
{

	private final String[]	extensionList			= { ".arpa", ".com", ".edu", ".firm", ".gov", ".int", ".mil", ".mobi", ".nato", ".net", ".nom", ".org", ".store", ".web" };

	private final String[]	countryExtensionList	= { ".ad", ".ae", ".af", ".ag", ".ai", ".al", ".am", ".an", ".ao", ".aq", ".ar", ".as", ".at", ".au", ".aw", ".ax", ".az", ".ba", ".bb", ".bd",
			".be", ".bf", ".bg", ".bh", ".bi", ".bj", ".bm", ".bn", ".bo", ".br", ".bs", ".bt", ".bv", ".bw", ".by", ".bz", ".ca", ".cc", ".cd", ".cf", ".cg", ".ch", ".ci", ".ck", ".cl", ".cm",
			".cn", ".co", ".cr", ".cs", ".cu", ".cv", ".cx", ".cy", ".cz", ".de", ".dj", ".dk", ".dm", ".do", ".dz", ".ec", ".ee", ".eg", ".eh", ".er", ".es", ".et", ".fi", ".fj", ".fk", ".fm",
			".fo", ".fr", ".fx", ".ga", ".gb", ".gd", ".ge", ".gf", ".gh", ".gi", ".gl", ".gm", ".gn", ".gp", ".gq", ".gr", ".gs", ".gt", ".gu", ".gw", ".gy", ".hk", ".hm", ".hn", ".hr", ".ht",
			".hu", ".id", ".ie", ".il", ".in", ".io", ".iq", ".ir", ".is", ".it", ".jm", ".jo", ".jp", ".ke", ".kg", ".kh", ".ki", ".km", ".kn", ".kp", ".kr", ".kw", ".ky", ".kz", ".la", ".lb",
			".lc", ".li", ".lk", ".lr", ".ls", ".lt", ".lu", ".lv", ".ly", ".ma", ".mc", ".md", ".mg", ".mh", ".mk", ".ml", ".mm", ".mn", ".mo", ".mp", ".mq", ".mr", ".ms", ".mt", ".mu", ".mv",
			".mw", ".mx", ".my", ".mz", ".na", ".nc", ".ne", ".nf", ".ng", ".ni", ".nl", ".no", ".np", ".nr", ".nu", ".nz", ".om", ".pa", ".pe", ".pf", ".pg", ".ph", ".pk", ".pl", ".pm", ".pn",
			".pr", ".ps", ".pt", ".pw", ".py", ".qa", ".re", ".ro", ".ru", ".rw", ".sa", ".sb", ".sc", ".sd", ".se", ".sg", ".sh", ".si", ".sj", ".sk", ".sl", ".sm", ".sn", ".so", ".sr", ".st",
			".su", ".sv", ".sy", ".sz", ".tc", ".td", ".tf", ".tg", ".th", ".tj", ".tk", ".tl", ".tm", ".tn", ".to", ".tp", ".tr", ".tt", ".tv", ".tw", ".tz", ".ua", ".ug", ".uk", ".um", ".us",
			".uy", ".uz", ".va", ".vc", ".ve", ".vg", ".vi", ".vn", ".vu", ".wf", ".ws", ".ye", ".yt", ".yu", ".za", ".zm", ".zr", ".zw" };

	public URLUtil()
	{
	}

	/**
	 * Normalizes the given url by replacing '/./' by '/' and removes trailing
	 * slashes
	 * 
	 * @param original
	 *            the original URL to be normalized
	 * @return the normalized url
	 */
	public String normalize(String aURL)
	{
		if (aURL != null)
		{

			aURL = normalizeDotFolders(aURL);
			aURL = normalizeBackSlashes(aURL);
			aURL = normalizeDoubleSlashes(aURL);
			aURL = normalizeAnchors(aURL);
			// aURL = normalizeStripQuery(aURL);
			// urlString = normalizeStripTrailingSlash(urlString) ;
		}
		return aURL;
	}

	private String normalizeAnchors(String aURL)
	{
		int p = aURL.indexOf('#');
		if (p > -1)
		{
			return aURL.substring(0, p);
		}
		else
		{
			return aURL;
		}
	}

	/**
	 * Replaces all backslashes by front slashes in the given url string
	 * 
	 * @param original
	 *            the original url string
	 * @return the url string with the normalization applied
	 */
	protected String normalizeBackSlashes(String original)
	{
		return StringUtil.replace(original, "\\", "/");
	}

	/**
	 * Replaces all double slashes by single slashes in the given url string
	 * 
	 * @param original
	 *            the original url string
	 * @return the url string with the normalization applied
	 */
	protected String normalizeDoubleSlashes(String original)
	{
		while (original.lastIndexOf("//") > 7)
		{
			original = StringUtil.replace(original, "//", "/", 7);
		}

		return original;
	}

	/**
	 * Removes all dot folders ( abc/./def/index.html, ...) from the given url
	 * string
	 * 
	 * @param original
	 *            the original url string
	 * @return the url string with the normalization applied
	 */
	protected String normalizeDotFolders(String original)
	{
		return StringUtil.replace(original, "/./", "/");
	}

	/**
	 * Strips an eventual query string from the resource (index.html?id=1
	 * becomes index.html for instance).
	 * 
	 * @param original
	 *            the original url string
	 * @return the url string with the normalization applied
	 */
	protected String normalizeStripQuery(String original)
	{
		int index = original.indexOf('?');
		if (index >= 0)
		{
			return original.substring(0, index);
		}
		else
		{
			return original;
		}
	}

	/**
	 * Removes an evantual trailing slash from the given url string
	 * 
	 * @param original
	 *            the original url string
	 * @return the url string with the normalization applied
	 */
	protected String normalizeStripTrailingSlash(String original)
	{
		if (original.endsWith("/"))
		{
			return original.substring(0, original.length() - 1);
		}
		else
		{
			return original;
		}
	}

	/**
	 * Converts any resource URL to the site's url.
	 * 
	 * @param resourceURL
	 *            the url of the resource to find the url of the site for
	 * @return the URL pointing to the site in which the resource is located
	 */
	public URL getSiteURL(URL resourceURL)
	{
		URL siteURL = null;
		if (resourceURL != null)
		{
			try
			{
				siteURL = new URL(resourceURL.getProtocol(), resourceURL.getHost(), resourceURL.getPort(), "");
			}
			catch (MalformedURLException e)
			{
				// shouldn't happen, we're only dropping the PATH part of a
				// valid URL ...
			}
		}
		return siteURL;
	}

	/**
	 * Reuturns the URL of the robots.txt resource in the site of the given
	 * resource.
	 * 
	 * @param resourceURL
	 *            the URL of the resource to find the site's robots.txt of
	 * @return URL pointing to the robots.txt resource of the site in which
	 *         resourceURL is
	 */
	public URL getRobotsTXTURL(URL resourceURL)
	{
		URL retVal = null;
		if (resourceURL != null)
		{
			try
			{
				retVal = new URL(getSiteURL(resourceURL), "/robots.txt");
			}
			catch (MalformedURLException e)
			{
			}
		}
		return retVal;
	}

	/**
	 * returns the resource path without the resource.
	 * 
	 * @param path
	 *            the path to the resource
	 * @return path without the resource itself
	 */
	public String stripResource(String path)
	{
		String result = null;
		if (path != null)
		{
			int pos = path.lastIndexOf("/");
			result = path.substring(0, pos + 1);
		}
		return result;
	}

	/**
	 * Returns the 'depth' of the resource pointed to by the URL
	 * 
	 * @param url
	 *            the URL to the resource to calculate the depth of
	 * @return the depth of this resource in the site
	 */
	public int getDepth(URL url)
	{
		int depth = 0;

		if (url != null)
		{
			String path = url.getPath();
			if (!isFileSpecified(url) && !path.endsWith("/"))
			{
				path = path + "/";
			}
			int pos = path.indexOf('/');
			while (pos != -1)
			{
				if (pos > 0)
				{
					depth++;
				}
				pos = path.indexOf('/', pos + 1);
			}
		}
		return depth;
	}

	/**
	 * Determines whether a file is specified in the path part of the url. This
	 * is assumed to be the case if the string after the last slash contains a
	 * dot (aaaaa/bbbb/cccc.dddd).
	 * 
	 * @param url
	 *            the url to test
	 * @return boolean value indicating whether a file is specified
	 */
	public boolean isFileSpecified(URL url)
	{
		boolean specified = false;

		String path = url.getPath();
		int posLastSlash = path.lastIndexOf('/');
		int posLastDot = path.lastIndexOf('.');

		specified = posLastDot > posLastSlash;

		return specified;
	}

	/**
	 * Returns an array of Strings being the folder names of the folders found
	 * in the given URL.
	 * 
	 * @param url
	 *            the url to parse the folders of
	 * @return an array of Strings containing all folder names
	 */
	public String[] getFolderNames(String aURL)
	{
		URL url = null;

		try
		{
			url = new URL(aURL);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		ArrayList<String> al = new ArrayList<String>();

		String path = url.getPath();
		if (isFileSpecified(url))
		{
			path = stripResource(path);
		}
		StringTokenizer st = new StringTokenizer(path, "/");

		while (st.hasMoreTokens())
		{
			al.add(st.nextToken());
		}
		return (String[]) al.toArray(new String[al.size()]);
	}

	/**
	 * Returns the file name (without the path) of the resource specified by the
	 * given url.
	 * 
	 * @param url
	 *            the url to get the filename out of
	 * @return String containing the name of the file, zero-length if none
	 */
	public String getFileName(URL url)
	{
		return url.getPath().substring(stripResource(url.getPath()).length());
	}

	/**
	 * Returns the file name (without the path) of the resource specified by the
	 * given url.
	 * 
	 * @param url
	 *            the url to get the filename out of
	 * @return String containing the name of the file, zero-length if none
	 */
	public String getFileName(String urlString)
	{
		String result = "";

		try
		{
			URL url = new URL(urlString);
			result = url.getPath().substring(stripResource(url.getPath()).length());
		}
		catch (MalformedURLException e)
		{

		}

		return result;
	}

	public String extractDomainName(String url) {
		String name = "";
		if (StringUtils.isNotBlank(url)) {

			try {
				URL urlObj = new URL(url);
				url = urlObj.getHost();
				if (!url.endsWith("/")) {
					url = url.concat("/");
				}
				String ext = getExtension(url);
				if (StringUtils.isNotBlank(ext)) {
					String[] s = url.split("\\.|/");
					for (int i = 0; i < s.length; i++) {
						if (s[i].equals(ext) && i > 0) {
							name = s[i - 1];
							break;
						}
					}
				}
			} catch (Throwable e) {

			}
		}

		return name;
	}

	private String getExtension(String hostName) {
		String result = "";
		for (String extension : extensionList) {
			if (hostName.contains(extension + ".") || hostName.endsWith(extension + "/")) {
				result = extension.replace(".", "");
				break;
			}
		}

		if (StringUtils.isBlank(result)) {
			for (String extension : countryExtensionList) {
				if (hostName.endsWith(extension + "/")) {
					result = extension.replace(".", "");
					break;
				}
			}
		}

		return result;
	}

	public boolean hostsAreEqual(String url1, String url2)
	{
		boolean result = false;
		String host1 = extractDomainName(url1);
		String host2 = extractDomainName(url2);
		if (StringUtils.isNotBlank(host1) && StringUtils.isNotBlank(host2))
		{
			result = host1.equals(host2);
		}
		return result;
	}

	public String createUrl(String baseUrl, String url)
	{
		String result = "";
		try
		{
			URL u = new URL(new URL(baseUrl), url);

			// remove unnecessary ../ from beginning of the path
			result = normalize(u.toExternalForm().replaceAll("\\.\\./", ""));

		}
		catch (Throwable e)
		{
			// do nothing
		}

		return result;
	}
	
	public String getRedirectedUrl(String urlString) {

		String result = urlString;

		HttpURLConnection.setFollowRedirects(false);
		int followRedirects = 10;
		int urlTimeout = 5000;

		try {

			URL url = new URL(urlString);
			HttpURLConnection httpConnection = null;
			URLConnection connection = null;

			while (followRedirects > 0) {

				connection = url.openConnection();

				if (connection instanceof HttpURLConnection) {

					httpConnection = (HttpURLConnection) connection;

					httpConnection.setConnectTimeout(urlTimeout);
					httpConnection.setReadTimeout(urlTimeout);
					httpConnection.connect();

					String location = httpConnection.getHeaderField("Location");

					if (location != null) {

						URL tmpURL = new URL(url, location);

						if (!url.toExternalForm().equals(
								tmpURL.toExternalForm())) {

							followRedirects--;
							url = tmpURL;
						} else {
							result = url.toExternalForm();
							break;
						}
					} else {
						result = url.toExternalForm();
						break;
					}
				}
			}
		} catch (Throwable e) {
			// do nothing
		}

		return result;
	}

	public static void main(String[] args)
	{
		URLUtil u = new URLUtil();
		System.out.println(u.getRedirectedUrl("http://www.e-bebek.com/xml.aspx?user=krnyrk001&pass=k001&type=karniyarik"));
	}

}
