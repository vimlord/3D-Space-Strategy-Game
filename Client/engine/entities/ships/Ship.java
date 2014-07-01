/*
 * NOTES ON SHIP MASSES:
 * Rotation speeds will be established so that the speed will be 5 degree/second
 * when the ship weighs 750 million kilograms
 * For a ship of that mass, the acceleration rate for firing engines will be 40 m/s^2
 * and it will have a health level of 100 units.
 */
 
package engine.entities.ships;
 
import engine.entities.*;
import engine.entities.ships.shipTools.orders.*;
import engine.entities.ships.shipTools.projectile_launchers.*;
import engine.gameMechanics.factions.*;
import engine.gameMechanics.generators.NameGenerator;
import java.util.ArrayList;
import engine.main.*;
 
/**
 *
 * @author Christopher Hittner
 */
public class Ship extends Entity implements ControlSystem, FactionTag{
    //Healths stats
    protected double maxHealth;
    protected double health;
    //Healing data
    protected double healthHealable, healRate = 1;
    protected boolean healingLimit = true;
    
    protected double maxShields;
    protected double shields, shieldChargeRate = 1;
    protected double cyclesSinceAttacked = 0;
    protected int secondsToStartCharge = 10, secondsToRebootShields = 20;
    
    protected double XZ_ROT = 0, Y_ROT = 0;
    protected double XZ_RotSpeed = 0, Y_RotSpeed = 0;
    protected double XZ_ROT_Target = 0, Y_ROT_Target = 0;
    
    protected boolean rotationTarget = false;
     
    protected double throttle = 0, engineForce = 30 * 750000000.0;
    protected double rotationRate = 1;
    
    //Warp Drive Stuff
    protected double warpCharge = 0;
    protected double warpCapacity = 600 * CycleRunner.cyclesPerSecond, warpMinimum = 100 * CycleRunner.cyclesPerSecond;
    protected boolean warpCharging = false, warping = false;
    protected double warpMode = 0, warpChargeRate;
    
    //The weapons on the Ship
    protected Railgun[] railguns;
    protected LaserGun[] lasers;
    protected MissileBattery[] missiles;
    
    //A cycling variable to help choose which weapons to fire
    private int tubeCount = 0;
    
    //The list of orders
    private ArrayList<Order> orders = new ArrayList<>();
    
    private int factionID;
    
    
    
    /**
     * @param X The x-coordinate
     * @param Y The x-coordinate
     * @param Z The x-coordinate
     * @param M The mass
     * @param R The radius/size of the ship's hit box
     * @param Railguns The number of Railguns the ship will have
     * @param Lasers The number of Laser Guns the ship will have
     * @param Missiles The number of Missile Batteries the ship will have
     * @param shieldFactor The strength of the ship's deflector shield
     */
    public Ship(double X, double Y, double Z, double R, int Railguns, int Lasers, int Missiles, double shieldFactor, int modifier){
        super(X, Y, Z, (4/3 * Math.PI * Math.pow(R, 3) * 40), R);
        //Outfits the ship with a railguns
        railguns = new Railgun[Railguns];
        for(int i = 0; i < railguns.length; i++){
            railguns[i] = new Railgun();
        }
        
        mass += Railgun.getMass() * Railguns; 
        
        
        //Outfits the ship with laser guns
        lasers = new LaserGun[Lasers];
        for(int i = 0; i < lasers.length; i++){
            lasers[i] = new LaserGun();
        }
        
        mass += LaserGun.getMass() * Lasers; 
        
        
        //Outfits the ship with missile launchers
        missiles = new MissileBattery[Missiles];
        for(int i = 0; i < missiles.length; i++){
            missiles[i] = new MissileBattery();
        }
        
        mass += MissileBattery.getMass() * Missiles; 
        
        
        //Sets the maximum and current health
        maxHealth = Math.sqrt(mass/75000);
        health = maxHealth;
        
        //Sets the amount of health the Ship is allowed to heal
        healthHealable = maxHealth;
        
        //Sets up the stats for the shields
        maxShields = shields * Math.sqrt(maxHealth);
        shields = maxShields;
        
        applyPerk(modifier);
        
        name = "S.S. " + NameGenerator.shipName();
    }
     
    /**
     * Accepts and processes a list of modifiers
     * @param modifiers The list of modifiers
     */
    public void applyPerk(int[] modifiers){
        int[] list = modifiers;
        //Sorts the list in smallest to largest order
        for(int i = 0; i < list.length; i++){
            for(int j = i + 1; j < list.length; j++){
                if(list[i] > list[j]){
                    int temp = list[i];
                    list[i] = list[j];
                    list[j] = temp;
                }
            }
        }
        for(int i = 0; i < list.length; i++){
            if(i != 0 && list[i] != list[i-1]){
                applyPerk(list[i]);
            }
        }
    }
    
    /*
     * Sets a modifier for this Ship object (NOTE: THE MODIFIER LIST IS UNDER REVIEW AND MODIFICATION)
     * 0: No modifier
     *
     * THE OLD LIST:
     * 1: Offensive Boost
     * 2: Defensive Boost
     * 3: Agility Boost
     *
     * THE NEW LIST:
     * Health/Shield Perks:
     * 1: Denser Alloys - Health is increased
     * 2: Regenerative Armor - Healing is unlimited
     * 3: Elite Engineering Corps - Healing is faster
     * 11: Shield Matrix Booster - Shields are stronger
     * 12: Shield Overclock - Shields recharge faster and immediately
     * 13: Shield Protocol Override - Shields don't wait to start healing
     * Movement/Warp Perks
     * 21: Warp Optimization Array - The warp drive has higher capacity
     * 22: Warp Energizer - The warp drive charges faster
     * 31: RCS System Overhaul
     * 32: Hermes Engines
     * Weapon Perks
     * 41: Additional Missile Tubes - Adds more missile tubes to the Ship
     * 42: Velociraptor Missiles - Missiles go faster more quickly and for longer
     * 43: HE Warheads - Missiles do more damage
     * 51: Electromagnetic Overclock - Slugs go faster
     * 52: Priming Optimization
     * 61: Emergency Ammunition Reserve
     *
     * @param modifierID The modifier to be used
     */
    public void applyPerk(int ID){
        
        
        if(ID == 1){
            maxHealth *= 1.5;
            health *= 1.5;
        } else if(ID == 2){
            healingLimit = false;
        } else if(ID == 3){
            healRate = 2;
        } else if(ID == 11){
            maxShields *= 1.5;
            shields *= 1.5;
        } else if(ID == 12){
            shieldChargeRate = 2;
        } else if(ID == 13){
            secondsToStartCharge = 0;
            secondsToRebootShields = 5;
        } else if(ID == 21){
            warpCapacity = 1200 * CycleRunner.cyclesPerSecond;
        } else if(ID == 22){
            warpChargeRate = 2;
        } else if(ID == 31){
            rotationRate = 2;
        } else if(ID == 32){
            engineForce = 60 * 750000000.0;
        } else if(ID == 41){
            missiles = new MissileBattery[(int)(1.5 * missiles.length)];
            for(int i = 0; i < missiles.length; i++){
                missiles[i] = new MissileBattery();
            }
        } else if(ID == 42){
            for(int i = 0; i < missiles.length; i++){
                missiles[i].toggleHECargo(true);
            }
        } else if(ID == 43){
            for(int i = 0; i < missiles.length; i++){
                missiles[i].toggleFastMissiles(true);
            }
        } else if(ID == 51){
            for(int i = 0; i < railguns.length; i++){
                railguns[i].toggleFastProjectile(true);
            }
        } else if(ID == 52){
            for(int i = 0; i < railguns.length; i++){
                railguns[i].toggleFastReload(true);
            }
        }
        /*
        if(modifierID == 1){
            
            //Increases amount of weapons
            Railgun[] r = new Railgun[(int)(1.1 * railguns.length)];
            for(int i = 0; i < r.length; i++){
                r[i] = new Railgun();
            }
            railguns = r;
            
            MissileBattery[] m = new MissileBattery[(int)(1.1 * missiles.length)];
            for(int i = 0; i < m.length; i++){
                m[i] = new MissileBattery();
            }
            missiles = m;
            
            LaserGun[] l = new LaserGun[(int)(1.1 * lasers.length)];
            for(int i = 0; i < l.length; i++){
                l[i] = new LaserGun();
            }
            lasers = l;
            
            //Decreases Defense
            maxHealth /= 1.1;
            health /= 1.1;
            maxShields /= 1.1;
            shields /= 1.1;
            
            //Decreases Agility
            mass *= 1.1;
            
            
        } else if(modifierID == 2){
            
            //Decreases amount of weapons
            Railgun[] r = new Railgun[(int)(railguns.length/1.1)];
            for(int i = 0; i < r.length; i++){
                r[i] = new Railgun();
            }
            railguns = r;
            
            MissileBattery[] m = new MissileBattery[(int)(missiles.length/1.1)];
            for(int i = 0; i < m.length; i++){
                m[i] = new MissileBattery();
            }
            missiles = m;
            
            LaserGun[] l = new LaserGun[(int)(lasers.length/1.1)];
            for(int i = 0; i < l.length; i++){
                l[i] = new LaserGun();
            }
            lasers = l;
            
            //Increases Defense
            maxHealth *= 1.1;
            health *= 1.1;
            maxShields *= 1.1;
            shields *= 1.1;
            
            //Decreases Agility
            mass *= 1.1;
            
        } if(modifierID == 3){
            //Decreases amount of weapons
            Railgun[] r = new Railgun[(int)(railguns.length/1.1)];
            for(int i = 0; i < r.length; i++){
                r[i] = new Railgun();
            }
            railguns = r;
            
            MissileBattery[] m = new MissileBattery[(int)(missiles.length/1.1)];
            for(int i = 0; i < m.length; i++){
                m[i] = new MissileBattery();
            }
            missiles = m;
            
            LaserGun[] l = new LaserGun[(int)(lasers.length/1.1)];
            for(int i = 0; i < l.length; i++){
                l[i] = new LaserGun();
            }
            lasers = l;
            
            //Decreases Defense
            maxHealth /= 1.1;
            health /= 1.1;
            maxShields /= 1.1;
            shields /= 1.1;
            
            //Increases Agility
            mass /= 1.1;
        } else {
            return;
        }
        
        */
        
    } 
     
     
    public void move(){
        autopilot();
        rotate();
        accelerate();
        super.move();
        warp();
        cycle();
        
    }
    
    public void cycle(){
        weaponCycle();
        healHealth(CycleRunner.getTimeWarp() * healRate);
        healShields(CycleRunner.getTimeWarp() * shieldChargeRate);
        chargeWarpDrive();
        if(tubeCount >= missiles.length){
            tubeCount -= missiles.length;
        }
    }
     
    
    //--------------------------------------------------------------------------
    //Regeneration and Damage
    //--------------------------------------------------------------------------
    
    /**
     * Heals a little bit of HP
     * @param factor The factor at which the ship's repair speed will be multiplied
     */
    public void healHealth(double factor){
        //Determines max health that will be healed this cycle
        double healthToHeal = factor * (Math.random()/CycleRunner.cyclesPerSecond) / 30.0;
        
        //Determines whether the amount will heal more than what is allowed
        if(healthToHeal > healthHealable){
            healthToHeal = healthHealable;
        }
        
        //Determines how much health has been lost
        double healthLost = maxHealth - health;
        
        //Makes sure the health doesn't go over the limit
        if(healthToHeal > healthLost){
            healthToHeal = healthLost;
        }
        
        //Heals the health
        if(healthLost >= healthToHeal){
            health += healthToHeal;
            if(healingLimit)
                healthHealable -= healthToHeal;
        }
    }
    
    public void healShields(double factor){
        double shieldPerSecond = factor;
        
        if(shields <= 0){
            //The shields are down; extra time will be needed to reboot the shields
            if(cyclesSinceAttacked < (CycleRunner.cyclesPerSecond * (secondsToStartCharge + secondsToRebootShields))){
                cyclesSinceAttacked+= factor;
            } else {
                shields += shieldPerSecond/CycleRunner.cyclesPerSecond;
            }
        } else {
            //The shields are up and operational, but need some time to start recharging
            if(cyclesSinceAttacked < (CycleRunner.cyclesPerSecond * secondsToStartCharge)){
                cyclesSinceAttacked+= factor;
            } else {
                shields += shieldPerSecond/CycleRunner.cyclesPerSecond;
            }
        }
        
    }
    
    public void damage(double damage){
        cyclesSinceAttacked = 0;
        shields -= damage;
        if(shields < 0){
            health += shields;
            shields = 0;
        }
        
    }
    
    //--------------------------------------------------------------------------
    //Rotation
    //--------------------------------------------------------------------------
     
    /**
     *
     * @param XZ The ratio amount of XZ to rotate by
     * @param Y The ratio amount of Y to rotate by
     * @param magnitude The percent of the ship's overall rotation ability to rotate by
     */
    public void setRotation(double XZ, double Y, double magnitude){
        double originalMagnitude = Math.sqrt(Math.pow(XZ, 2) + Math.pow(Y, 2));
        double horiz = (XZ/originalMagnitude) * (magnitude/100);
        double vert = (Y/originalMagnitude) * (magnitude/100);
        if(XZ == 0 && Y == 0){
            XZ_RotSpeed = 0;
            Y_RotSpeed = 0;
        } else {
            XZ_RotSpeed = 10000.0 * Math.toRadians((10000000000.0/mass) * horiz)/CycleRunner.cyclesPerSecond;
            Y_RotSpeed = 10000.0 * Math.toRadians((7500000000.0/mass) * vert)/CycleRunner.cyclesPerSecond;
        }
    }
     
    private void rotationCorrection(){
        //Corrections in case the ship rotates beyond a certain degree value
        if(Y_ROT > Math.toRadians(90.0)){
            Y_ROT = Math.toRadians(180.0) - Y_ROT;
            Y_ROT_Target -= Math.toRadians(180.0);
            Y_RotSpeed *= -1.0;
            XZ_ROT += Math.toRadians(180.0);
            XZ_ROT_Target += Math.toRadians(180.0);
        } else if(Y_ROT < Math.toRadians(-90.0)){
            Y_ROT = Math.toRadians(-180.0) - Y_ROT;
            Y_ROT_Target += Math.toRadians(180.0);
            Y_RotSpeed *= -1.0;
            XZ_ROT += Math.toRadians(180);
            XZ_ROT_Target += Math.toRadians(180.0);
        }
        if(XZ_ROT >= Math.toRadians(360.0)){
            XZ_ROT -= Math.toRadians(360.0);
            XZ_ROT_Target -= Math.toRadians(360.0);
        } else if(XZ_ROT < 0.0){
            XZ_ROT += Math.toRadians(360.0);
            XZ_ROT_Target += Math.toRadians(360.0);
        }
    }
    
    /**
     * Performs rotation based on specified values
     */
    public void rotate(){
        rotationCorrection();
        
        XZ_ROT += rotationRate * CycleRunner.getTimeWarp() * XZ_RotSpeed/CycleRunner.cyclesPerSecond;
        Y_ROT += rotationRate * CycleRunner.getTimeWarp() * Y_RotSpeed/CycleRunner.cyclesPerSecond;
    }
    
    public void setRotationTarget(double XZ, double Y){
        XZ_ROT_Target = XZ;
        Y_ROT_Target = Y;
        rotationTarget = true;
    }
    
    
    //--------------------------------------------------------------------------
    //Autopilot
    //--------------------------------------------------------------------------
    
    
    public void autopilot(){
        //Use of Orders
        String order;
        try{
            order = orders.get(0).getOrder();
        } catch(Exception e){
            order = "";
        }
        
        //This is added as a countermeasure against any crashes that could occur
        //due to attempting to creating substrings of blank or null integers
        if(order.equals("") || order.equals(null)){
            order = "(   )";
        }
        
        if(orders.size() > 0){
            for(int i = orders.size() - 1; i >= 1; i--){
                if(orders.get(0).getClass().equals(orders.get(i).getClass())){
                    orders.remove(i);
                }
            }
        }
        
        //This will make sure that if a Ship doesn't have rotation orders or
        //acceleration orders, it won't accelerate
        setAcceleration(0);
        setRotationTarget(XZ_ROT,Y_ROT);
        
        if(order.substring(0,5).equals("(ACC)")){
            //This is an acceleration order
            setAcceleration(Double.parseDouble(order.substring(5,order.indexOf("|"))));
            if(!orders.get(0).getStatus()){
                orders.remove(0);
            }
        } else if(order.substring(0,5).equals("(ROT)")){
            //This is an rotation order
            int border = order.indexOf("|");
            double XZ = Double.parseDouble(order.substring(5, border));
            double Y = Double.parseDouble(order.substring(border + 1));
            
            setRotationTarget(XZ, Y);
            rotationCorrection();
            
            orders.remove(0);
            orders.add(0,new Rotate(XZ_ROT_Target,Y_ROT_Target));
            
            if(Math.abs(Y_ROT - Y) < Math.toRadians(200.0/CycleRunner.cyclesPerSecond) && Math.abs(XZ_ROT - XZ) < Math.toRadians(200.0/CycleRunner.cyclesPerSecond)){
                orders.remove(0);
            }
        } else if(order.equals("(WAIT)")){
            //Does nothing; I added this just to make sure nothing happens.
            if(!orders.get(0).getStatus()){
                orders.remove(0);
            }
        } else if(order.substring(0,5).equals("(WRP)")){
            if(!warping){
                if(warpCharge >= warpMinimum){
                    setWarp(Double.parseDouble(order.substring(5,order.indexOf("|"))));
                } else {
                    orders.remove(0);
                    setWarp(0);
                }
            }
            
            if(!orders.get(0).getStatus()){
                setWarp(0);
                orders.remove(0);
            }
        } else if(order.substring(0,5).equals("(MNV)")){
            orders.remove(0);
            String data = order.substring(6);
            double magnitude = Double.parseDouble(data.substring(1,data.indexOf(")")));
            data = data.substring(data.indexOf(")") + 1);
            double XZ = Double.parseDouble(data.substring(1,data.indexOf(")")));
            data = data.substring(data.indexOf(")") + 1);
            double Y = Double.parseDouble(data.substring(1,data.indexOf(")")));
            orders.add(0, new Rotate(XZ,Y));
            
            double accelerationTime = magnitude/(30 * 750000000.0 / mass);
            orders.add(1, new Accelerate(100,accelerationTime));
            
        }
        for(Order o : orders){
            //Tests for an Attack order. The Railgun will have to be coded
            //separately, as the Ship must point directly AT the target
            if(o instanceof Attack){
                order = o.getOrder();
                
                boolean fireMissile = (order.substring(6, 7).equals("T"));
                boolean fireLaser = (order.substring(7, 8).equals("T"));
                boolean fireRailgun = (order.substring(8, 9).equals("T"));
                
                long targ = Long.parseLong(order.substring(10));
                
                Faction targFaction = FactionList.getFaction(((Ship)(EntityList.getEntity(targ))).getFactionID());
                Faction thisFaction = FactionList.getFaction(factionID);
                if(!(thisFaction.getDiplomaticStatus(targFaction) != 1) && thisFaction.getID() == targFaction.getID()){
                    break;
                }
                
                
                if(fireMissile){
                    fireMissiles(tubeCount,targ);
                    tubeCount++;
                }
                
                if(fireLaser){
                    for(int i = 0; i < lasers.length; i++){
                        fireLasers(i,targ);
                    }
                }
                if(fireRailgun){
                    for(int i = 0; i < railguns.length; i++){
                        fireRailgun(i);
                    }
                }
                
                if(!o.getStatus()){
                    orders.remove(o);
                }
                
                break;
            } else if(o instanceof Wait){
                break;
            }
        }
        
        
        if(rotationTarget){
            int xz = 0;
            int y = 0;
            
            if(Math.abs(XZ_ROT - XZ_ROT_Target) < Math.toRadians(500/CycleRunner.cyclesPerSecond)){
                xz = 0;
            } else if(XZ_ROT < XZ_ROT_Target){
                xz = 1;
            } else if(XZ_ROT > XZ_ROT_Target){
                xz = -1;
            }
            
            if(Math.abs(Y_ROT - Y_ROT_Target) < Math.toRadians(500/CycleRunner.cyclesPerSecond)){
                y = 0;
            } else if(Y_ROT < Y_ROT_Target){
                y = 1;
            } else if(Y_ROT > Y_ROT_Target){
                y = -1;
            }
            
            setRotation(xz, y, 100);
            
            if(xz == 0 && y == 0){
                rotationTarget = false;
            }
            
        }
    }
 
    
    //--------------------------------------------------------------------------
    //Acceleration
    //--------------------------------------------------------------------------
    
    /**
     * Sets the value of the throttle to determine how quickly the ship will accelerate
     * @param thrustPercent The throttle level, in percent
     */
    public void setAcceleration(double thrustPercent){
        if(thrustPercent >= 0 && thrustPercent <= 100)
            throttle = thrustPercent;
    }
     
    /**
     * Accelerates the ship in the direction it is pointing
     */
    public void accelerate() {
        double force = engineForce * throttle/100;
        velX += CycleRunner.getTimeWarp() * Math.cos(XZ_ROT) * Math.cos(Y_ROT) * (force/mass)/CycleRunner.cyclesPerSecond;
        velZ += CycleRunner.getTimeWarp() * Math.sin(XZ_ROT) * Math.cos(Y_ROT) * (force/mass)/CycleRunner.cyclesPerSecond;
        velY += CycleRunner.getTimeWarp() * Math.sin(Y_ROT) * (force/mass)/CycleRunner.cyclesPerSecond;
    }
    
    
    //--------------------------------------------------------------------------
    //Warp Drive
    //--------------------------------------------------------------------------
    
    
    /**
     * Sets the warp mode of the Ship (in multiples of the speed of light)
     * @param level
     */
    public void setWarp(double level){
        if(level > 0){
            warpMode = level;
        }
    }
    
    /**
     * Determines whether the Ship object is prepping for a warp jump
     * @param b
     */
    public void setWarpPrep(boolean b){
        warpCharging = b;
    }
    
    /**
     * Powers the warp drive up if it can be done
     */
    public void chargeWarpDrive(){
        if(((warpMode == 0 && warpCharging) || warpCharge < warpMinimum)){
            
            warpCharge += (10000000/mass) * 200 * warpChargeRate;
            
            if(warpCharge > warpCapacity){
                warpCharge = warpCapacity;
            }
            
        } else if(warpMode > 0){
            
            warpCharging = false;
            warpCharge -= Math.pow(warpMode,2) * (mass/10000000);
            
            if(warpCharge < 0){
                warpCharge = 0;
            }
            
        }
    }
    
    
    /**
     * Performs a frame of the warp jump in the physics of the game
     */
    public void warp(){
        if(!warping){
            if(warpCharge >= warpMinimum && warpMode > 0){
                warping = true;
            }
        } else {
            if(warpMode == 0){
                warping = false;
            }
        }
        
        //Error check that keeps people from using an infinitely large warp power
        if(Math.pow(warpMode,2) > warpCharge){
            warpMode = (int)(Math.sqrt(warpCharge));
        }
        
        if(warping){
            x += CycleRunner.getTimeWarp() * (c * warpMode) * Math.cos(XZ_ROT) * Math.cos(Y_ROT) / CycleRunner.cyclesPerSecond;
            y += CycleRunner.getTimeWarp() * (c * warpMode) * Math.sin(Y_ROT) / CycleRunner.cyclesPerSecond;
            z += CycleRunner.getTimeWarp() * (c * warpMode) * Math.sin(XZ_ROT) * Math.cos(Y_ROT) / CycleRunner.cyclesPerSecond;
        }
    }
    
    public double getWarpCharge(){
        return warpCharge;
    }
    
    public double getMaxWarpCharge(){
        return warpCapacity;
    }
    
    
    //---------------------------------------------------------------------------
    //Collision stuff
    //---------------------------------------------------------------------------
    
    public void collide(Ship other){
        setWarp(0);
        other.setWarp(0);
        double relVelX = (this.velX) - (other.velX);
        double relVelY = (this.velY) - (other.velY);
        double relVelZ = (this.velZ) - (other.velZ);
        double relVel = Math.sqrt(Math.pow(relVelX,2) + Math.pow(relVelY,2) + Math.pow(relVelY,2));
        
        this.damage(Math.sqrt(other.mass/this.mass) * Math.pow(relVel,4) / this.mass);
        
        other.damage(Math.sqrt(this.mass/other.mass) * Math.pow(relVel,4) / other.mass);
        
        super.collide(other);
    }
    
    public void collide(Entity other){
        setWarp(0);
        double relVelX = this.velX - other.getSpeedX();
        double relVelY = this.velY - other.getSpeedY();
        double relVelZ = this.velZ - other.getSpeedZ();
        double relVel = Math.sqrt(Math.pow(relVelX,2) + Math.pow(relVelY,2) + Math.pow(relVelY,2));
        
        damage(Math.sqrt(other.getMass(false)/this.mass) * Math.pow(relVel,4) / this.mass);
        
        super.collide(other);
    }
    
    
    
    
    
    //--------------------------------
    //Railgun Shooter
    //--------------------------------
    public void fireRailgun(int index){
        try {
            railguns[index].fire(this);
        } catch (Exception ex){
            
        }
    }
    
    //--------------------------------
    //Laser Shooter
    //--------------------------------
    public void fireLasers(int index){
        try {
            lasers[index].fire(this);
        } catch (Exception ex){
            
        }
    }
    public void fireLasers(int index, long target){
        try {
            lasers[index].fire(this,EntityList.getEntity(target));
        } catch (Exception ex){
            
        }
    }
    
    
    //--------------------------------
    //Missile Shooter
    //--------------------------------
    public void fireMissiles(int index){
        try {
            missiles[index].fire(this);
        } catch (Exception ex){
            
        }
    }
    public void fireMissiles(int index, long target){
        try {
            missiles[index].fire(this,EntityList.getEntity(target));
        } catch (Exception ex){
            
        }
    }
    
    
    //--------------------------------
    //Weapon Cycling
    //--------------------------------
    private void weaponCycle() {
        for(Railgun r : railguns){
            r.cycle();
        }
        for(MissileBattery m : missiles){
            m.cycle();
        }
        for(LaserGun l : lasers){
            l.cycle();
        }
            
    }
    
    
    //--------------------------------
    //Orders
    //--------------------------------
    public void giveOrders(Order o){
        orders.add(o);
    }
    public void giveOrders(ArrayList<Order> order){
        for(Order o : order)
            orders.add(o);
    }
    
    public void cancelOrders(int index){
        orders.remove(index);
    }
    
    public void stopAttacking(){
        for(Order o : orders){
            if(o instanceof Attack){
                orders.remove(o);
            }
        }
    }
    
    
    //---------------------------
    //Data return methods
    //---------------------------
    
    
    /**
     * Returns the ship's health
     * @return The Ship's health
     */
    public double getHealth(){
        return health;
    }
    
    /**
     * Returns the ship's maximum health
     * @return The Ship's maximum health
     */
    public double getMaxHealth(){
        return maxHealth;
    }
    
    /**
     * Returns the ship's shields
     * @return The Ship's shields
     */
    public double getShields(){
        return shields;
    }
    
    /**
     * Returns the ship's maximum shields
     * @return The Ship's maximum shields
     */
    public double getMaxShields(){
        return maxShields;
    }
    
    /**
     * Returns the rotation on the XZ plane
     * @return The XZ Rotation
     */
    public double getXZ_ROT(){
        return XZ_ROT;
    }
    
    /**
     * Returns the rotation on the Y axis
     * @return The Y Rotation
     */
    public double getY_ROT(){
        return Y_ROT;
    }
    /**
     * Returns the rotation speed on the XZ plane
     * @return The XZ Rotation speed
     */
    public double getXZ_RotSpeed(){
        return XZ_RotSpeed;
    }
    
    /**
     * Returns the rotation speed on the Y axis
     * @return The Y Rotation speed
     */
    public double getY_RotSpeed(){
        return Y_RotSpeed;
    }

    @Override
    public long setFactionID(int ID) {
        Faction joining = FactionList.getFaction(ID);
        if(joining == null)
            return -1;
        FactionList.getFaction(ID).addMember(this);
        factionID = ID;
        return getID();
    }

    @Override
    public int getFactionID() {
        return factionID;
    }

    public String[] getTag(){
        String[] tag = new String[2];
        tag[0] = name;
        tag[1] = "Health: " + (int)(100 * health / maxHealth) + "%";
        
        return tag;
    }
    
    
}

