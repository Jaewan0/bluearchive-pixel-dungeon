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
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StoryScene extends PixelScene{
    private Image background;
    private Image banner;
    private Image eden;
    private IconButton btnFade; //only on landscape
    private StyledButton choiceBtn1, choiceBtn2;
    private StyledButton textBtn;
    private IconButton btnExit;
    private Image nagisa;
    private RenderedTextBlock text;
    private RenderedTextBlock charaName;
    private RenderedTextBlock charaDesc;
    private float uiAlpha;
    private int check = 0;


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
        banner.visible = banner.active = false;


        background = new Image(Assets.Background.TRINITY_TERRACE) {
            public void update() {
                if (choiceBtn1.visible) {
                    if (rm > 1f) {
                        rm -= Game.elapsed;
                        gm = bm = rm;
                    } else {
                        rm = gm = bm = 1;
                    }
                }
            }
        };

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
        choiceBtn1 = new StyledButton(Chrome.Type.CHOICE_BUTTON, "") {
            @Override
            protected void onClick() {
                choiceBtn1.visible = false;
                choiceBtn2.visible = false;
                if (check == 0) {
                    StoryScene.this.text.text("후훗. 빠르게 용건만 끝내는 것도 좋지만\n" +
                            "가끔은 애프터눈 티를 하면서 이야기를 나누는건 어떠신가요?");
                }
                else if (check == 1) {
                    StoryScene.this.text.text("어떤 음료로 드릴까요?");
                }
                else if (check == 2) {
                    StoryScene.this.text.text("바로 본론으로 들어가서...\n" +
                            "저번 사건 이후로 꽤 많은 생각이 들었습니다.");
                    Music.INSTANCE.play(Assets.Music.THEME_105, true);
                }
                else if (check == 13) {
                    Music.INSTANCE.play(Assets.Music.MX_AD, true);
                    StoryScene.this.text.text("그럼요. 걱정해주셔서 감사합니다.\n" +
                            "그래서, 선생님께 부탁드리고 싶은 일이 있습니다. 바로 트리니티의 지하 카타콤에 대한 조사를.");
                }
                else if (check == 14) {
                    StoryScene.this.text.text("네. 트리니티 지하에 있지만 저희 역시 그 실상을 거의 모르고\n" +
                            "앞으로 카타콤이 어떤 식으로 이용될지 모르는 일이니까요.");
                }
                else if (check == 15) {
                    StoryScene.this.text.text("선생님께서 좋아해주신다니 감사합니다.\n" +
                            "그럼, 선생님도 동의하신 걸로 알고 카타콤 정찰을 의뢰하겠습니다.\n" +
                            "같이 함께 할 학생으로는…");
                }
                else if (check == 16) {
                    ShatteredPixelDungeon.switchScene(HeroSelectScene.class);
                }
                check++;
            }
        };
        choiceBtn1.setPos(Camera.main.width / 2f - 100, Camera.main.height / 2f - 300);
        choiceBtn1.text("나기사가 불러서 왔어");
        choiceBtn1.textColor(Window.TEXT_COLOR);
        add(choiceBtn1);
        choiceBtn1.visible = false;

        choiceBtn2 = new StyledButton(Chrome.Type.CHOICE_BUTTON,"") {
            @Override
            protected void onClick() {
                choiceBtn1.visible = false;
                choiceBtn2.visible = false;
                if (check == 0) {
                    StoryScene.this.text.text("후훗. 빠르게 용건만 끝내는 것도 좋지만\n" +
                            "가끔은 홍차라도 마시면서 이야기를 나누는건 어떠신가요? 이야기가 꽤 길어질거 같으니까요.");
                } else if (check == 15) {
                    StoryScene.this.text.text("선생님께서 좋아해주신다니 감사합니다.\n" +
                            "그럼, 선생님도 동의하신 걸로 알고 카타콤 정찰을 의뢰하겠습니다.\n" +
                            "같이 함께 할 학생으로는…");
                } else if (check == 16) {
                    ShatteredPixelDungeon.switchScene(HeroSelectScene.class);
                }
                check++;
            }

        };
        choiceBtn2.setPos(Camera.main.width / 2f - 100, Camera.main.height / 2f - 300);
        choiceBtn2.text("무슨 일이야 나기사?");
        choiceBtn2.textColor(Window.TEXT_COLOR);
        add(choiceBtn2);
        choiceBtn2.visible = false;

        textBtn = new StyledButton(Chrome.Type.TEXT_BUTTON, "") {
            @Override
            protected void onClick() {
                super.onClick();
                textBtn.alpha(0.5f);
                if (check == 0) {
                    choiceBtn1.visible = true;
                    choiceBtn2.visible = true;
                }
                else if (check == 1) {
                    choiceBtn1.visible = true;
                    choiceBtn1.text("나기사의 바람대로 의자에 앉았다.");
                }
                else if (check == 2) {
                    choiceBtn1.visible = true;
                    choiceBtn1.text("홍차로 부탁해.");
                }
                else if (check == 3) {
                    StoryScene.this.text.visible = false;
                    StoryScene.this.charaName.visible = false;
                    StoryScene.this.charaDesc.visible = false;
                    eden = new Image(Assets.Background.EDEN_1);
                    eden.scale.set(Camera.main.width/eden.width, Camera.main.height/eden.height);
                    eden.x = (Camera.main.width - background.width())/2f;
                    eden.y = (Camera.main.height - background.height())/2f;
                    PixelScene.align(eden);
                    add(eden);
                    check++;
                    fadeIn();
                }
                else if (check == 4) {
                    eden.visible = false;
                    eden = new Image(Assets.Background.EDEN_2);
                    eden.scale.set(Camera.main.width/eden.width, Camera.main.height/eden.height);
                    eden.x = (Camera.main.width - background.width())/2f;
                    eden.y = (Camera.main.height - background.height())/2f;
                    PixelScene.align(eden);
                    add(eden);
                    check++;
                    fadeIn();
                }
                else if (check == 5) {
                    eden.visible = false;
                    eden = new Image(Assets.Background.EDEN_3);
                    eden.scale.set(Camera.main.width/eden.width, Camera.main.height/eden.height);
                    eden.x = (Camera.main.width - background.width())/2f;
                    eden.y = (Camera.main.height - background.height())/2f;
                    PixelScene.align(eden);
                    add(eden);
                    check++;
                    fadeIn();
                }
                else if (check == 6) {
                    eden.visible = false;
                    eden = new Image(Assets.Background.EDEN_4);
                    eden.scale.set(Camera.main.width/eden.width, Camera.main.height/eden.height);
                    eden.x = (Camera.main.width - background.width())/2f;
                    eden.y = (Camera.main.height - background.height())/2f;
                    PixelScene.align(eden);
                    add(eden);
                    check++;
                    fadeIn();
                }
                else if (check == 7) {
                    eden.visible = false;
                    eden = new Image(Assets.Background.EDEN_5);
                    eden.scale.set(Camera.main.width/eden.width, Camera.main.height/eden.height);
                    eden.x = (Camera.main.width - background.width())/2f;
                    eden.y = (Camera.main.height - background.height())/2f;
                    PixelScene.align(eden);
                    add(eden);
                    check++;
                    fadeIn();
                }
                else if (check == 8) {
                    eden.visible = false;
                    eden = new Image(Assets.Background.EDEN_6);
                    eden.scale.set(Camera.main.width/eden.width, Camera.main.height/eden.height);
                    eden.x = (Camera.main.width - background.width())/2f;
                    eden.y = (Camera.main.height - background.height())/2f;
                    PixelScene.align(eden);
                    add(eden);
                    check++;
                    fadeIn();
                }
                else if (check == 9) {
                    eden.visible = false;
                    eden = new Image(Assets.Background.EDEN_7);
                    eden.scale.set(Camera.main.width/eden.width, Camera.main.height/eden.height);
                    eden.x = (Camera.main.width - background.width())/2f;
                    eden.y = (Camera.main.height - background.height())/2f;
                    PixelScene.align(eden);
                    add(eden);
                    check++;
                    fadeIn();
                } else if (check == 10) {
                    eden.visible = false;
                    eden = new Image(Assets.Background.EDEN_8);
                    eden.scale.set(Camera.main.width/eden.width, Camera.main.height/eden.height);
                    eden.x = (Camera.main.width - background.width())/2f;
                    eden.y = (Camera.main.height - background.height())/2f;
                    PixelScene.align(eden);
                    add(eden);
                    check++;
                    fadeIn();
                } else if (check == 11) {
                    eden.visible = false;
                    eden = new Image(Assets.Background.EDEN_11);
                    eden.scale.set(Camera.main.width/eden.width, Camera.main.height/eden.height);
                    eden.x = (Camera.main.width - background.width())/2f;
                    eden.y = (Camera.main.height - background.height())/2f;
                    PixelScene.align(eden);
                    add(eden);
                    check++;
                    fadeIn();
                } else if (check == 12) {
                    eden.visible = false;
                    eden = new Image(Assets.Background.EDEN_12);
                    eden.scale.set(Camera.main.width/eden.width, Camera.main.height/eden.height);
                    eden.x = (Camera.main.width - background.width())/2f;
                    eden.y = (Camera.main.height - background.height())/2f;
                    PixelScene.align(eden);
                    add(eden);
                    check++;
                    fadeIn();
                } else if (check == 13) {
                    eden.visible = false;
                    StoryScene.this.text.visible = true;
                    StoryScene.this.charaName.visible = true;
                    StoryScene.this.charaDesc.visible = true;
                    choiceBtn1.visible = true;
                    choiceBtn1.text("괜찮아, 나기사?");
                } else if (check == 14) {
                    choiceBtn1.visible = true;
                    choiceBtn1.text("카타콤?");
                } else if (check == 15) {
                    choiceBtn1.visible = true;
                    choiceBtn1.text("그런 부탁이라면 언제나 환영이야.");
                    choiceBtn2.visible = true;
                    choiceBtn2.text("학생들의 안전을 위해서라면 당연히 들어줘야지.");
                }
                else if (check == 16) {
                    ShatteredPixelDungeon.switchScene(HeroSelectScene.class);
                }

            }
        };
        textBtn.setPos(Camera.main.width / 2f - 100, Camera.main.height / 2f - 300);
        textBtn.alpha(0.5f);
        add(textBtn);

        text = renderTextBlock("어서오세요, 선생님", 8);
        text.setPos(Camera.main.width / 2f - 157, Camera.main.height / 2f + 55);
        add(text);

        charaName = renderTextBlock("나기사", 9);
        charaName.setPos(Camera.main.width / 2f - 150, Camera.main.height / 2f + 40);
        add(charaName);
        charaDesc = renderTextBlock("티파티", 7);
        charaDesc.setPos(Camera.main.width / 2f - 120, Camera.main.height / 2f + 42);
        add(charaDesc);

        if (landscape()) {
            choiceBtn1.setRect(Camera.main.width / 2f - 100, Camera.main.height / 2f - 30, 200, 17);
            align(choiceBtn1);
            choiceBtn2.setRect(choiceBtn1.left(), Camera.main.height / 2f, choiceBtn1.width(), choiceBtn1.height());
            textBtn.setRect(Camera.main.width / 2f - 300, Camera.main.height / 2f + 35, background.width, 70);
        }
        else {
            choiceBtn1.setRect(Camera.main.width / 2f - 100, Camera.main.height / 2f - 30, 200, 17);
            align(choiceBtn1);
            choiceBtn2.setRect(choiceBtn1.left(), Camera.main.height / 2f, choiceBtn1.width(), choiceBtn1.height());
            textBtn.setRect(Camera.main.width / 2f - 300, Camera.main.height / 2f + 35, background.width,70);
        }
        btnExit = new ExitButton();
        btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
        add( btnExit );
        btnExit.visible = btnExit.active = !SPDSettings.intro();
        fadeIn();

    }

    @Override
    public void update() {
        super.update();
        btnExit.visible = btnExit.active = !SPDSettings.intro();
    }

    private void updateFade() {
        float alpha = GameMath.gate(0f, uiAlpha, 1f);
        choiceBtn1.enable(alpha != 0);
        choiceBtn1.alpha(alpha);
    }
    protected void onBackPressed() {
        if (btnExit.active){
            ShatteredPixelDungeon.switchScene(TitleScene.class);
        } else {
            super.onBackPressed();
        }
    }
}
