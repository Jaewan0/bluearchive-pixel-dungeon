package com.shatteredpixel.shatteredpixeldungeon.scenes;

import com.shatteredpixel.shatteredpixeldungeon.*;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.journal.Journal;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.ExitButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.StyledButton;
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
import com.watabou.noosa.Camera;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.PointerArea;
import com.watabou.noosa.tweeners.Tweener;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.GameMath;
import com.watabou.utils.PointF;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StoryScene extends PixelScene{
    private Image background;
    private Image fadeLeft, fadeRight;
    private IconButton btnFade; //only on landscape
    private Image nagisa;
    private StyledButton textBtn;

    @Override
    public void create() {
        super.create();
        Badges.loadGlobal();
        Journal.loadGlobal();


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

        textBtn = new StyledButton(Chrome.Type.GREY_BUTTON_TR, ""){
            @Override
            protected void onClick() {
                super.onClick();

            }
        };
    }
}
