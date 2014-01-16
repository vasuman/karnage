package com.bleatware.karnage;

import com.bleatware.karnage.entities.*;

import java.util.ArrayList;

/**
 * Ator
 * User: vasuman
 * Date: 1/9/14
 * Time: 5:15 PM
 */
public class GameState {
    public void deactivate(Powerup powerup) {
        powerup.setActivated(false);
    }

    public void activate(Powerup powerup) {
        powerup.setActivated(true);
    }

    public void tryUnlock(Powerup powerup) {
        powerup.unlock();
        addCoins(-powerup.getCost());
    }

    public static class Powerup {
        public static interface DefModifier {
            public void modify(Player.PlayerDef def);
        }

        private int cost;
        private String name;
        private String description;
        private String imagePath;
        private DefModifier modifier;
        private boolean unlocked = false;
        private boolean activated = false;

        public Powerup(int cost, String name, String description, String imagePath, DefModifier modifier) {
            this.cost = cost;
            this.name = name;
            this.description = description;
            this.imagePath = imagePath;
            this.modifier = modifier;
        }

        public void apply(Player.PlayerDef def) {
            modifier.modify(def);
        }

        public boolean isActivated() {
            return activated;
        }

        public boolean isUnlocked() {
            return unlocked;
        }

        public int getCost() {
            return cost;
        }

        private void setActivated(boolean value) {
            if (!unlocked) {
                return;
            }
            activated = value;
        }

        private void unlock() {
            unlocked = true;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getImagePath() {
            return imagePath;
        }
    }

    private ArrayList<Powerup> powerups = new ArrayList<Powerup>();
    private int coins;

    public GameState() {
        Powerup.DefModifier tapGunModifier = new Powerup.DefModifier() {
            @Override
            public void modify(Player.PlayerDef def) {
                def.builders.add(new TapGun.TapGunBuilder());
            }
        };
        coins = 12;
        Powerup tapGunPowerUp = new Powerup(1, "Tap Gun", "Tap on screen to fire at that position", "ui/tapgun", tapGunModifier);

        tapGunPowerUp.unlock();
        tapGunPowerUp.setActivated(true);
        powerups.add(tapGunPowerUp);
        Powerup.DefModifier gyroMoveModifier = new Powerup.DefModifier() {
            @Override
            public void modify(Player.PlayerDef def) {
                def.builders.add(new GyroMove.GyroMoveBuilder(500));
            }
        };
        Powerup gyroPowerUp = new Powerup(1, "Gyro Move", "Tilt device to move player around", "ui/gyromove", gyroMoveModifier);
        gyroPowerUp.unlock();
        gyroPowerUp.setActivated(true);
        powerups.add(gyroPowerUp);
        Powerup.DefModifier touchGunModifier = new Powerup.DefModifier() {
            @Override
            public void modify(Player.PlayerDef def) {
                def.builders.add(new TouchGun.TouchGunBuilder(3));
            }
        };
        Powerup touchGunPowerUp = new Powerup(1, "Automatic Gun", "Hold finger on screen for continuous fire", "ui/tapgun", touchGunModifier);
        powerups.add(touchGunPowerUp);

        Powerup.DefModifier stickControlModifier = new Powerup.DefModifier() {
            @Override
            public void modify(Player.PlayerDef def) {
                def.builders.add(new StickGun.StickGunBuilder(3));
            }
        };
        Powerup stickGunPowerUp = new Powerup(1, "Joystick Gun", "Fire with an on-screen joystick", "ui/joystick", stickControlModifier);
        powerups.add(stickGunPowerUp);

        Powerup.DefModifier stickMoveModifier = new Powerup.DefModifier() {
            @Override
            public void modify(Player.PlayerDef def) {
                def.builders.add(new StickMove.StickMoveBuilder(80));
            }
        };
        Powerup stickMovePowerUp = new Powerup(1, "Joystick Move", "Move around with an on-screen joystick", "ui/joystick", stickMoveModifier);
        powerups.add(stickMovePowerUp);
    }

    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public Player.PlayerDef getPlayerDef() {
        Player.PlayerDef playerDef = new Player.PlayerDef();
        initDefaults(playerDef);
        for (Powerup powerup : powerups) {
            if (powerup.isActivated()) {
                powerup.apply(playerDef);
            }
        }
        return playerDef;
    }

    private void initDefaults(Player.PlayerDef playerDef) {
        playerDef.health = 10;
        playerDef.modelPath = "player-base.g3db";
        playerDef.size = 16;
        playerDef.damp = 0.95f;
        playerDef.builders = new ArrayList<Extension.ExtensionBuilder>();
    }
}
