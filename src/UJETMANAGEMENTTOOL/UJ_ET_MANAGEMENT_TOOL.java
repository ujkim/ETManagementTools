package UJETMANAGEMENTTOOL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;



public class UJ_ET_MANAGEMENT_TOOL extends Application {

    private static final int INFINITE = -1;
    private static final int PACKET_COUNT = 10;
    // BPF filter for capturing any packet
    private static final String FILTER = "";
    private PacketCapture m_pcap;
    private String m_device;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UJETMainView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("UJETMANAGEMENT TOOL V0.9");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Step 1: Instantiate Capturing Engine
        m_pcap = new PacketCapture();

        // Step 2: Check for devices
        m_device = m_pcap.findDevice();

        // Step 3: Open Device for Capturing (requires root)
        m_pcap.open(m_device, true);

        // Step 4: Add a BPF Filter (see tcpdump documentation)
        m_pcap.setFilter(FILTER, true);

        // Step 5: Register a Listener for Raw Packets
        m_pcap.addRawPacketListener(new RawPacketHandler());

        //Step 6: Capture Data (max. PACEKT_COUNT packets)
        m_pcap.capture(PACKET_COUNT);
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}

class RawPacketHandler implements RawPacketListener
{
    private static int m_counter = 0;
    public void rawPacketArrived(RawPacket data) {
        m_counter++;
        System.out.println("Received packet (" + m_counter + ")");
    }
}
