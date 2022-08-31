package com.github.ginirohikocha;

import io.objectbox.sync.Sync;
import io.objectbox.sync.SyncBuilder;
import io.objectbox.sync.SyncClient;
import io.objectbox.sync.SyncCredentials;
import io.objectbox.sync.listener.SyncCompletedListener;
import io.objectbox.sync.listener.SyncConnectionListener;
import io.objectbox.sync.listener.SyncLoginListener;
import org.junit.jupiter.api.*;

/**
 * @author GinirohikoCha
 * @since 2022/8/31
 * Need to sign up to get your free ObjectBox Sync trial on official page
 * start server method -> https://sync.objectbox.io/objectbox-sync-server
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ObjectBoxSyncTest {

    private static final String DATABASE1_NAME = "ObjectBox-Sync-1";
    private static final String DATABASE2_NAME = "ObjectBox-Sync-2";

    private static ObjectBoxManager manager1;
    private static ObjectBoxManager manager2;

    @BeforeAll
    public static void initObjectBox() {
        manager1 = ObjectBoxManager.getInstance(DATABASE1_NAME);
        manager2 = ObjectBoxManager.getInstance(DATABASE2_NAME);
    }

    @Test
    public void isSyncAvailable() {
        String syncAvailable = Sync.isAvailable() ? "available" : "unavailable";
        // need to implement objectbox-sync-windows
        System.out.println("ObjectBox Sync is " + syncAvailable);
    }

    private static SyncClient syncClient;

    @Test
    @Order(1)
    public void StartSyncClient() {
        SyncBuilder builder = Sync.client(
                    manager1.getStore(),
                    "ws://127.0.0.1" /* Use wss for encrypted traffic. */,
                    SyncCredentials.sharedSecret("GinirohikoCha") /* SyncCredentials.none() */
        );
        syncClient = builder
                .loginListener(loginListener)
                .completedListener(completedListener)
                .connectionListener(connectListener)
                .buildAndStart(); // Connect and start syncing.
    }

    @Test
    @Order(2)
    public void main() throws InterruptedException {
        int seconds = 0;
        while (seconds++ < 30) {
            System.out.println("Running...");
            Thread.sleep(1000);
        }
    }

    @AfterAll
    public static void closeObjectBox() {
        manager1.close();
        manager2.close();
    }


    /*
    * Listener
    * */
    SyncLoginListener loginListener = new SyncLoginListener() {
        @Override
        public void onLoggedIn() {
            // Login successful.
            System.out.println("onLoggedIn");
        }

        @Override
        public void onLoginFailed(long syncLoginCode) {
            // Login failed. Returns one of SyncLoginCodes.
            System.out.println("onLoginFailed");
        }
    };

    SyncCompletedListener completedListener = new SyncCompletedListener() {
        @Override
        public void onUpdatesCompleted() {
            // A sync has completed, client is up-to-date.
            System.out.println("onUpdatesCompleted");
        }
    };

    SyncConnectionListener connectListener = () -> {
        // Client disconnected from the server.
        // Depending on the configuration it will try to re-connect.
        System.out.println("onDisconnected");
    };
}
