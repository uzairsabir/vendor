package com.thetechsolutions.whodouvendor.Chat.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ColleagesDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.CustomersDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import eu.siacs.conversation.entities.Conversation;
import eu.siacs.conversation.entities.Message;
import eu.siacs.conversation.entities.Transferable;
import eu.siacs.conversation.utils.UIHelper;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Uzair on 3/25/2016.
 */
@LayoutId(R.layout.conversation_list_row_chat)
public class ChatListAdapter extends ItemViewHolder<Conversation> {


    @ViewId(R.id.fresco_view)
    SimpleDraweeView sourceImageView;

    @ViewId(R.id.conversation_name)
    TextView title;

    @ViewId(R.id.conversation_lastmsg)
    TextView lmessage;

    @ViewId(R.id.conversation_lastupdate)
    TextView datetime;

    @ViewId(R.id.conversation_lastimage)
    ImageView prevImage;

    @ViewId(R.id.container)
    RelativeLayout container;


    static Activity activity;
    CustomersDT customersDT = new CustomersDT();
    ColleagesDT colleagesDT = new ColleagesDT();

    Conversation item_;

    public ChatListAdapter(View view) {
        super(view);
    }

    public static Class<ChatListAdapter> newInstance(Activity _activity) {
        activity = _activity;

        return ChatListAdapter.class;
    }


    @Override
    public void onSetValues(Conversation item, PositionInfo positionInfo) {
        //    MyLogs.printinfo("name_test  " + item.getJid().toBareJid().toString() + " : " + RealmDataRetrive.getHomeListOfMyProviderAndMyFriends().get(0));
        item_=item;
        try {
//            for (CustomersDT items : RealmDataRetrive.getHomeListOfMyProviderAndMyFriends()) {
//
//                if (item.getJid().toBareJid().toString().contains(items.getUsername())) {
//                    providerDT = items;
//                    break;
//
//                }
//            }

            for (CustomersDT items : RealmDataRetrive.getCustomerList()) {

                if (item.getJid().toBareJid().toString().split("@")[0].equals(items.getUsername()+"_c")) {
                    customersDT = items;
                    break;

                }
            }
            for (ColleagesDT items : RealmDataRetrive.getColleagesList()) {

                // MyLogs.printinfo(items.getUsername()+"_c");
                if (item.getJid().toBareJid().toString().split("@")[0].equals(items.getUsername()+"_v")) {
                    colleagesDT = items;
                    break;

                }
            }
//            if (UtilityFunctions.isEmpty(providerDT.getFirst_name())) {
//                title.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, item.getJid().toBareJid().toString().split("_")[0]));
//                sourceImageView.setImageURI(Uri.parse("test.png"));
//            } else {
//                title.setText(providerDT.getFirst_name() + " " + providerDT.getLast_name());
//                sourceImageView.setImageURI(Uri.parse(providerDT.getImage_url()));
//            }

            try{
                if (UtilityFunctions.isEmpty(customersDT.getFirst_name())) {
                    // title.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, item.getJid().toBareJid().toString().split("_")[0]));
                    //sourceImageView.setImageURI(Uri.parse("test.png"));
                    if (UtilityFunctions.isEmpty(colleagesDT.getFirst_name())) {
                        title.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, item.getJid().toBareJid().toString().split("_")[0]));
                        sourceImageView.setImageURI(Uri.parse("test.png"));
                    } else {
                        title.setText(colleagesDT.getFirst_name() + " " + colleagesDT.getLast_name());
                        sourceImageView.setImageURI(Uri.parse(colleagesDT.getImage_url()));
                    }
                } else {
                    title.setText(customersDT.getFirst_name() + " " + customersDT.getLast_name());
                    sourceImageView.setImageURI(Uri.parse(customersDT.getImage_url()));
                }
            }catch (Exception e){

            }

        } catch (Exception e) {
            try {
                MyLogs.printinfo("getid " + item.getJid().toBareJid().toString());
                title.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, item.getJid().toBareJid().toString().split("_")[0]));
                sourceImageView.setImageURI(Uri.parse("test.png"));
            } catch (Exception a) {
                title.setText("Unknown");
                sourceImageView.setImageURI(Uri.parse("test.png"));
            }

        }
//        title.setText("Testing Name ");
        //       sourceImageView.setImageURI("abc.png");
        // Conversation conversation = getItem(position);
        //  SimpleDraweeView profilePicture = (SimpleDraweeView) view.findViewById(R.id.conversation_image);

//		if (this.activity instanceof ConversationActivity) {
//			View swipeableItem = view.findViewById(R.id.swipeable_item);
//			ConversationActivity a = (ConversationActivity) this.activity;
//			int c = a.highlightSelectedConversations() && conversation == a.getSelectedConversation() ? a.getSecondaryBackgroundColor() : a.getPrimaryBackgroundColor();
//			swipeableItem.setBackgroundColor(c);
//		}
        //  TextView convName = (TextView) view.findViewById(R.id.conversation_name);
        //if (conversation.getMode() == Conversation.MODE_SINGLE ) {

//		} else {
//			//convName.setText(conversation.getJid().toBareJid().toString());
//		}
        // TextView mLastMessage = (TextView) view.findViewById(R.id.conversation_lastmsg);
        // TextView mTimestamp = (TextView) view.findViewById(R.id.conversation_lastupdate);
        //SimpleDraweeView imagePreview = (SimpleDraweeView) view.findViewById(R.id.conversation_lastimage);
        // ImageView notificationStatus = (ImageView) view.findViewById(R.id.notification_status);

//        Conversation
        Message message = getItem().getLatestMessage();

        if (!getItem().isRead()) {
            title.setTypeface(null, Typeface.BOLD);
        } else {
            title.setTypeface(null, Typeface.NORMAL);
        }

        if (message.getFileParams().width > 0
                && (message.getTransferable() == null
                || message.getTransferable().getStatus() != Transferable.STATUS_DELETED)) {
            lmessage.setText("Photo");
            //mLastMessage.setVisibility(View.GONE);
            //imagePreview.setVisibility(View.VISIBLE);
            //imagePreview.setImageURI(Uri.parse(message.));
            // activity.loadBitmap(message, imagePreview);
        } else {
            Pair<String, Boolean> preview = UIHelper.getMessagePreview(activity, message);
            lmessage.setVisibility(View.VISIBLE);
            prevImage.setVisibility(View.GONE);
            lmessage.setText(preview.first);
            if (preview.second) {
                if (getItem().isRead()) {
                    lmessage.setTypeface(null, Typeface.ITALIC);
                } else {
                    lmessage.setTypeface(null, Typeface.BOLD_ITALIC);
                }
            } else {
                if (getItem().isRead()) {
                    lmessage.setTypeface(null, Typeface.NORMAL);
                } else {
                    lmessage.setTypeface(null, Typeface.BOLD);
                }
            }
        }


        datetime.setText(UIHelper.readableTimeDifference(activity, getItem().getLatestMessage().getTimeSent()));

        //  RelativeLayout container = (RelativeLayout) view.findViewById(R.id.container);


    }

    @Override
    public void onSetListeners() {
        super.onSetListeners();
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String username="";
//                try{
//                    if (UtilityFunctions.isEmpty(providerDT.getFirst_name())) {
//                        username=UtilityFunctions.getFormattedNumberToDisplay(activity, getItem().getJid().toBareJid().toString().split("_")[0]);
//                    }else{
//                        username=providerDT.getFirst_name() + " " + providerDT.getLast_name();
//                    }
//
//                }catch (Exception e){
//
//                }
//
//
//
//                if (getItem().getJid().toBareJid().toString().split("_")[1].equalsIgnoreCase("v")) {
//
//                    AppController.openChat(activity, getItem().getJid().toBareJid().toString().split("_")[0], username, providerDT.getImage_url(), providerDT.getIs_register_user(), 1);
//
//                } else {
//                    MyLogs.printerror("image_url "+providerDT.getImage_url());
//                    AppController.openChat(activity, getItem().getJid().toBareJid().toString().split("_")[0], username, providerDT.getImage_url(), providerDT.getIs_register_user(), 0);
//
//                }






                String username="";

                if (item_.getJid().toBareJid().toString().split("@")[0].contains("_c")) {

                    try{
                        if (UtilityFunctions.isEmpty(customersDT.getFirst_name())) {
                            username=UtilityFunctions.getFormattedNumberToDisplay(activity, getItem().getJid().toBareJid().toString().split("_")[0]);
                        }else{
                            username=customersDT.getFirst_name() + " " + customersDT.getLast_name();
                        }

                    }catch (Exception e){

                    }

                    AppController.openChat(activity, getItem().getJid().toBareJid().toString().split("_")[0], username, customersDT.getImage_url(), customersDT.getIs_register_user(), 0);

                }   if (item_.getJid().toBareJid().toString().split("@")[0].contains("_v")) {
                    try{
                        if (UtilityFunctions.isEmpty(colleagesDT.getFirst_name())) {
                            username=UtilityFunctions.getFormattedNumberToDisplay(activity, getItem().getJid().toBareJid().toString().split("_")[0]);
                        }else{
                            username=colleagesDT.getFirst_name() + " " + colleagesDT.getLast_name();
                        }

                    }catch (Exception e){

                    }
                    AppController.openChat(activity, getItem().getJid().toBareJid().toString().split("_")[0], username, colleagesDT.getImage_url(), colleagesDT.getIs_register_user(), 1);

                }






            }
        });


    }


    public interface Listener {
        void onButtonClicked(ScheduleDT datetype);
    }

}
