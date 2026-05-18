package project.interfaces;
import project.models.CreateEmployee;
import project.models.Message;
public interface CanSendMessage {
    Message sendMessage(CreateEmployee to, String text);
}
