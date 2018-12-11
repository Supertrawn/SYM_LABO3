package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

/**
 * @Interface   : CommunicationEventListener
 * @Author(s)   : Michael Brouchoud, Thomas Lechaire & Kevin Pradervand
 * @Date        : 11.12.2018
 *
 * @Goal        : Listen the event to process the response
 *
 * @Comment(s)  : -
 */
public interface CommunicationEventListener {
    /**
     * @brief Process the response
     * @param response The response to process
     */
    void handleResponse(Boolean response);
}