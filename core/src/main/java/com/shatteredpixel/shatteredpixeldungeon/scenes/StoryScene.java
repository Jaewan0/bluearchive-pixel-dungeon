package com.shatteredpixel.shatteredpixeldungeon.scenes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.shatteredpixel.shatteredpixeldungeon.*;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.journal.Journal;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.*;
import com.shatteredpixel.shatteredpixeldungeon.ui.Button;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTextInput;
import com.shatteredpixel.shatteredpixeldungeon.utils.DungeonSeed;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndChallenges;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndHeroInfo;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndKeyBindings;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.gltextures.TextureCache;
import com.watabou.input.PointerEvent;
import com.watabou.noosa.*;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.tweeners.Tweener;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.ColorMath;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.GameMath;
import com.watabou.utils.PointF;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StoryScene extends PixelScene{
    private Image background;
    private Image banner;
    private IconButton btnFade; //only on landscape
    private Image nagisa;

    @Override
    public void create() {
        super.create();
        Music.INSTANCE.play(Assets.Music.MX_AD, true);
        Badges.loadGlobal();
        Journal.loadGlobal();
        banner = new Image(Assets.Interfaces.STORY_BANNER);
        banner.scale.set(Camera.main.width/banner.width, Camera.main.height/banner.height);
        banner.x = (Camera.main.width - banner.width())/2f;
        banner.y = (Camera.main.height - banner.height())/2f;
        PixelScene.align(banner);
        add(banner);


        background = new Image(Assets.Background.TRINITY_TERRACE);

        background.scale.set(Camera.main.width/background.width, Camera.main.height/background.height);

        background.x = (Camera.main.width - background.width())/2f;
        background.y = (Camera.main.height - background.height())/2f;
        PixelScene.align(background);
        add(background);

        nagisa = new Image(Assets.Character.NAGISA);
        nagisa.scale.set(Camera.main.height/nagisa.height);

        nagisa.x = (Camera.main.width - nagisa.width())/2f;
        nagisa.y = (Camera.main.height - nagisa.height() + 50)/2f;
        PixelScene.align(nagisa);
        add(nagisa);
        StyledButton choiceBtn1 = new StyledButton(Chrome.Type.CHOICE_BUTTON, "") {
            @Override
            protected void onClick() {
                nagisa.visible = false;
            }
        };
        choiceBtn1.setPos(Camera.main.width / 2f - 100, Camera.main.height / 2f - 300);
        choiceBtn1.text("나기사가 불러서 왔어");
        choiceBtn1.textColor(Window.TEXT_COLOR);
        add(choiceBtn1);

        StyledButton choiceBtn2 = new StyledButton(Chrome.Type.CHOICE_BUTTON,"") {
            @Override
            protected void onClick() {
                nagisa.visible = true;
            }

        };
        choiceBtn2.setPos(Camera.main.width / 2f - 100, Camera.main.height / 2f - 300);
        choiceBtn2.text("무슨 일이야 나기사?");
        choiceBtn2.textColor(Window.TEXT_COLOR);
        add(choiceBtn2);

        if (landscape()) {
            choiceBtn1.setRect(Camera.main.width / 2f - 100, Camera.main.height / 2f - 30, 200, 17);
            align(choiceBtn1);
            choiceBtn2.setRect(choiceBtn1.left(), Camera.main.height / 2f, choiceBtn1.width(), choiceBtn1.height());
        }
        else {
            choiceBtn1.setRect(Camera.main.width / 2f - 100, Camera.main.height / 2f - 30, 200, 17);
            align(choiceBtn1);
            choiceBtn2.setRect(choiceBtn1.left(), Camera.main.height / 2f, choiceBtn1.width(), choiceBtn1.height());
        }

    }
}
