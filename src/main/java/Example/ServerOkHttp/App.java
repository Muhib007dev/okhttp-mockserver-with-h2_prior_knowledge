package Example.ServerOkHttp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.List;

import javax.net.ServerSocketFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import okio.BufferedSource;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
//		System.out.println("Hello World!");

		MockWebServer server = new MockWebServer();
		
		server.setProtocols(Collections.singletonList(Protocol.H2_PRIOR_KNOWLEDGE));
		server.enqueue(new MockResponse().setBody("hello"));
		server.enqueue(new MockResponse().setBody("hello"));
		server.enqueue(new MockResponse().setBody("hello"));
		server.start(8080);

//		OkHttpClient client = new OkHttpClient.Builder()
//				.protocols(Collections.singletonList(Protocol.H2_PRIOR_KNOWLEDGE)).build();
//
//		Request request = new Request.Builder().url("http://localhost:8487/").build();
//
//		Response response = client.newCall(request).execute();
//		System.out.println(response.body().string());

//		server.shutdown();

//		MockWebServer server = new MockWebServer();
//		server.setProtocols(Collections.singletonList(Protocol.H2_PRIOR_KNOWLEDGE));
//		server.enqueue(new MockResponse().setBody("hello"));
//		server.start(8787);
//		
	}

	public static void testFetchData() throws InterruptedException, IOException {
		// mock_response.json is placed on 'androidTest/assets/'
		final InputStream stream = new FileInputStream("src/main/java/Example/ServerOkHttp/mock_response.json");

		OkHttpClient client = new OkHttpClient();

//		OkHttpClient httpClient = new OkHttpClient()
//				.newBuilder()
//				.addInterceptor
//				(new Interceptor() {
//			public Response intercept(Chain arg0) throws IOException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		}).build();

		client.newBuilder().interceptors().add(new Interceptor() {
			public Response intercept(Chain chain) throws IOException {
				return new Response.Builder().protocol(Protocol.HTTP_2)
						// This is essential as it makes response.isSuccessful() returning true.
						.code(200).request(chain.request()).body(new ResponseBody() {
							@Override
							public MediaType contentType() {
								return null;
							}

							@Override
							public long contentLength() {
								// Means we don't know the length beforehand.
								return -1;
							}

							@Override
							public BufferedSource source() {
								try {
									return new Buffer().readFrom(stream);
								} catch (IOException e) {
									e.printStackTrace();
									return null;
								}
							}
						}).build();
			}
		});

//		api.fetchData();

		// TODO: Let's assert the data here.
	}
}

//// Start the server.
//server.start();
//
//// Ask the server for its URL. You'll need this to make HTTP requests.
//HttpUrl baseUrl = server.url("/v1/chat/");
//
//// Exercise your application code, which should make those HTTP requests.
//// Responses are returned in the same order that they are enqueued.
//Chat chat = new Chat(baseUrl);
//
//chat.loadMore();
//assertEquals("hello, world!", chat.messages());
//
//chat.loadMore();
//chat.loadMore();
//assertEquals(""
//  + "hello, world!\n"
//  + "sup, bra?\n"
//  + "yo dog", chat.messages());
//
//// Optional: confirm that your app made the HTTP requests you were expecting.
//RecordedRequest request1 = server.takeRequest();
//assertEquals("/v1/chat/messages/", request1.getPath());
//assertNotNull(request1.getHeader("Authorization"));
//
//RecordedRequest request2 = server.takeRequest();
//assertEquals("/v1/chat/messages/2", request2.getPath());
//
//RecordedRequest request3 = server.takeRequest();
//assertEquals("/v1/chat/messages/3", request3.getPath());
//
//// Shut down the server. Instances cannot be reused.