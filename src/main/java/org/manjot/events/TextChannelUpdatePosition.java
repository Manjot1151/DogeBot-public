// package org.manjot.events;
/*package org.manjot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdatePositionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnTextChannelUpdatePosition extends ListenerAdapter
{
    @Override
    public void onTextChannelUpdatePosition(TextChannelUpdatePositionEvent event)
    {
        JDA jda = event.getJDA();
        String info = "";
        //info += event.toString() + "\n\n";
        info += "Moved Channel: " +  event.getChannel().getAsMention() + " (" + event.getOldPosition() + " -> " + event.getNewPosition() + ")\n";
        // info += event.getEntity().getAsMention() + "\n";
        // info += "Channel type: " + event.getEntityType().toString();
        // info += "Response Number: " + event.getResponseNumber() + "\nOldValue-NewValue: " + event.getOldValue() + "->" + event.getNewValue();
        jda.getTextChannelById(731861019533639740L).sendMessage(info).queue();
    }
}
*/