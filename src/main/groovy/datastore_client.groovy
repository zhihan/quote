package me.zhihan.quote

import com.google.api.services.datastore.client.Datastore
import com.google.api.services.datastore.client.DatastoreException
import com.google.api.services.datastore.client.DatastoreFactory
import com.google.api.services.datastore.client.DatastoreOptions
import com.google.api.services.datastore.DatastoreV1.Entity
import com.google.api.services.datastore.DatastoreV1.CommitRequest
import com.google.api.services.datastore.DatastoreV1.Mutation
import com.google.api.services.datastore.DatastoreV1.LookupRequest
import com.google.api.services.datastore.DatastoreV1.Key
import com.google.api.services.datastore.client.DatastoreHelper
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential

import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport

import java.util.List

/** A Google Cloud datastore client using service accound */
class DatastoreClient {
    private String projectId = "famous-quote"
    private String serviceAccount = "1054142814373-cqo4md7nf37qbbd277mqvtq6l5g9bpq7@developer.gserviceaccount.com"
    private String privateP12KeyFile = "quote-ebc651c43da8.p12"
    private List<String> scopes = ["https://www.googleapis.com/auth/userinfo.email",
      "https://www.googleapis.com/auth/datastore"]
    private NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport()
    private JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance()

    private Datastore datastore 
  
    public DatastoreClient() {
        URL url = ClassLoader.getSystemClassLoader().getResource(privateP12KeyFile)
        GoogleCredential credential = new GoogleCredential.Builder()
            .setTransport(httpTransport)
            .setJsonFactory(jsonFactory)
            .setServiceAccountId(serviceAccount)
            .setServiceAccountPrivateKeyFromP12File(new File(url.getPath()))
            .setServiceAccountScopes(scopes)
            .build()

        DatastoreOptions option = new DatastoreOptions.Builder()
            .credential(credential)
            .dataset(projectId).build()

        datastore = DatastoreFactory.get().create(option)
    }

    public void savePerson(Person p) {
        Entity.Builder builder = Entity.newBuilder()
            .setKey(DatastoreHelper.makeKey("Person", p.person))
            .addProperty(DatastoreHelper.makeProperty("firstName",
                DatastoreHelper.makeValue(p.firstName)))
            .addProperty(DatastoreHelper.makeProperty("lastName",
                DatastoreHelper.makeValue(p.lastName)))
            .addProperty(DatastoreHelper.makeProperty("profession",
                DatastoreHelper.makeValue(p.profession)))
        CommitRequest req = CommitRequest.newBuilder()
            .setMode(CommitRequest.Mode.NON_TRANSACTIONAL)
            .setMutation(Mutation.newBuilder().addInsert(builder)).build()
        datastore.commit(req)
    }


}
