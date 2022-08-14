package top.mizushio.rika;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class YSArtifact {


    static public Map<String, String> artifactKeyToCN = new HashMap<>();
    static public Map<String, String> statKeyToCN = new HashMap<>();
    static public Map<String, String> slotKeyToCN = new HashMap<>();
    static public Map<String, String> charaToCN = new HashMap<>();
    static public Map<String, String> CNToArtifact;
    static public Map<String, String> CNToStatKey;
    static public Map<String, String> CNToSlotKey;
    static public Map<String, String> CNToChara;

    static public Map<String, String> dbStatKeyToCN = new HashMap<>();
    static public Map<String, String> dbSlotKeyToCN = new HashMap<>();

    static public Map<String, Double> values = new HashMap<>();
    static public final Map<String, Double> defaultWeight = new HashMap<>();
    static public Map<String, Double> weight = defaultWeight;

    static MODE mode;

    static List<Integer> lockSwitch = new ArrayList<>();

    static JSONArray oriAllArtifacts;

    static DecimalFormat df = new DecimalFormat("0.00");

    static {
        artifactKeyToCN.put("ArchaicPetra", "悠古的磐岩");//岩伤
        artifactKeyToCN.put("HeartOfDepth", "沉沦之心"); //少用 攻击
        artifactKeyToCN.put("BlizzardStrayer", "冰风迷途的勇士");
        artifactKeyToCN.put("RetracingBolide", "逆飞的流星");//护盾强效 前台攻击
        artifactKeyToCN.put("NoblesseOblige", "昔日宗室之仪");//元素爆发
        artifactKeyToCN.put("GladiatorsFinale", "角斗士的终幕礼");
        artifactKeyToCN.put("MaidenBeloved", "被怜爱的少女");//治疗
        artifactKeyToCN.put("ViridescentVenerer", "翠绿之影");//风伤 扩散 减抗
        artifactKeyToCN.put("Lavawalker", "渡过烈火的贤人");//火抗 火伤
        artifactKeyToCN.put("CrimsonWitchOfFlames", "炽烈的炎之魔女");//火伤  反应
        artifactKeyToCN.put("Thundersoother", "平息雷鸣的尊者");//雷抗
        artifactKeyToCN.put("ThunderingFury", "如雷的盛怒");
        artifactKeyToCN.put("BloodstainedChivalry", "染血的骑士道");//物伤
        artifactKeyToCN.put("WanderersTroupe", "流浪大地的乐团");//元素精通 重击
        artifactKeyToCN.put("Scholar", "学士");
        artifactKeyToCN.put("Gambler", "赌徒");
        artifactKeyToCN.put("TinyMiracle", "奇迹");
        artifactKeyToCN.put("MartialArtist", "武人");
        artifactKeyToCN.put("BraveHeart", "勇者之心");
        artifactKeyToCN.put("ResolutionOfSojourner", "行者之心");
        artifactKeyToCN.put("DefenderWill", "守护之心");
        artifactKeyToCN.put("Berserker", "血战之人");
        artifactKeyToCN.put("Instructor", "教官");
        artifactKeyToCN.put("Exile", "流放者");
        artifactKeyToCN.put("Adventurer", "冒险家");
        artifactKeyToCN.put("LuckyDog", "幸运儿");
        artifactKeyToCN.put("TravelingDoctor", "游医");
        artifactKeyToCN.put("PrayersForWisdom", "祭雷之人");
        artifactKeyToCN.put("PrayersToSpringtime", "祭冰之人");
        artifactKeyToCN.put("PrayersForIllumination", "祭火之人");
        artifactKeyToCN.put("PrayersForDestiny", "祭水之人");
        artifactKeyToCN.put("PaleFlame", "苍白之火");//
        artifactKeyToCN.put("TenacityOfTheMillelith", "千岩牢固");//攻击力 护盾 生命
        artifactKeyToCN.put("EmblemOfSeveredFate", "绝缘之旗印");  //元素充能
        artifactKeyToCN.put("ShimenawasReminiscence", "追忆之注连");//攻击
        artifactKeyToCN.put("HuskOfOpulentDreams", "华馆梦醒形骸记"); //岩 防御
        artifactKeyToCN.put("OceanHuedClam", "海染砗磲");//生命值  攻击力
        artifactKeyToCN.put("VermillionHereafter", "辰砂往生录");
        artifactKeyToCN.put("EchoesOfAnOffering", "来歆余响");//攻击


        statKeyToCN.put("hp", "生命值");
        statKeyToCN.put("hp_", "百分比生命值");
        statKeyToCN.put("atk", "攻击力");
        statKeyToCN.put("atk_", "百分比攻击力");
        statKeyToCN.put("def", "防御力");
        statKeyToCN.put("def_", "百分比防御力");
        statKeyToCN.put("critRate_", "暴击率");//
        statKeyToCN.put("critDMG_", "暴击伤害");//
        statKeyToCN.put("eleMas", "元素精通");//
        statKeyToCN.put("enerRech_", "元素充能");//
        statKeyToCN.put("heal_", "治疗加成");//
        statKeyToCN.put("electro_dmg_", "雷元素伤害加成");//
        statKeyToCN.put("pyro_dmg_", "火元素伤害加成");//
        statKeyToCN.put("hydro_dmg_", "水元素伤害加成");//
        statKeyToCN.put("cryo_dmg_", "冰元素伤害加成");//
        statKeyToCN.put("anemo_dmg_", "风元素伤害加成");//
        statKeyToCN.put("geo_dmg_", "岩元素伤害加成");//
        statKeyToCN.put("physical_dmg_", "物理伤害加成");//

        values.put("critDMG_", 7.77);
        values.put("critRate_", 3.88);
        values.put("atk_", 5.83);
        values.put("atk", 19.50);
        values.put("hp_", 5.83);
        values.put("hp", 298.83);
        values.put("def_", 7.28);
        values.put("def", 23.33);
        values.put("enerRech_", 6.48);
        values.put("eleMas", 23.33);

        defaultWeight.put("critDMG_", 1d);
        defaultWeight.put("critRate_", 1d);
        defaultWeight.put("atk_", 0.5d);
        defaultWeight.put("atk", 0d);
        defaultWeight.put("hp_", 0.01d);
        defaultWeight.put("hp", 0d);
        defaultWeight.put("def_", 0.01d);
        defaultWeight.put("def", 0d);
        defaultWeight.put("enerRech_", 0.5d);
        defaultWeight.put("eleMas", 0.5d);

        dbStatKeyToCN.put("HPFixed", "生命值");
        dbStatKeyToCN.put("HPPercentage", "百分比生命值");
        dbStatKeyToCN.put("ATKFixed", "攻击力");
        dbStatKeyToCN.put("ATKPercentage", "百分比攻击力");
        dbStatKeyToCN.put("ElementalMastery", "元素精通");
        dbStatKeyToCN.put("DEFFixed", "防御力");
        dbStatKeyToCN.put("DEFPercentage", "百分比防御力");
        dbStatKeyToCN.put("CriticalRate", "暴击率");
        dbStatKeyToCN.put("CriticalDamage", "暴击伤害");
        dbStatKeyToCN.put("Recharge", "元素充能");
        dbStatKeyToCN.put("HealingBonus", "治疗加成");
        dbStatKeyToCN.put("ElectroBonus", "雷元素伤害加成");
        dbStatKeyToCN.put("PyroBonus", "火元素伤害加成");
        dbStatKeyToCN.put("HydroBonus", "水元素伤害加成");
        dbStatKeyToCN.put("CryoBonus", "冰元素伤害加成");
        dbStatKeyToCN.put("AnemoBonus", "风元素伤害加成");
        dbStatKeyToCN.put("GeoBonus", "岩元素伤害加成");
        dbStatKeyToCN.put("PhysicalBonus", "物理伤害加成");


        slotKeyToCN.put("flower", "花");
        slotKeyToCN.put("plume", "羽");
        slotKeyToCN.put("sands", "沙");
        slotKeyToCN.put("goblet", "杯");
        slotKeyToCN.put("circlet", "冠");


        dbSlotKeyToCN.put("Flower", "花");
        dbSlotKeyToCN.put("Feather", "羽");
        dbSlotKeyToCN.put("Sand", "沙");
        dbSlotKeyToCN.put("Goblet", "杯");
        dbSlotKeyToCN.put("Head", "冠");


        charaToCN.put("Albedo", "阿贝多");
        charaToCN.put("Aloy", "埃洛伊");
        charaToCN.put("Amber", "安柏");
        charaToCN.put("AratakiItto", "荒泷一斗");
        charaToCN.put("Barbara", "芭芭拉");
        charaToCN.put("Beidou", "北斗");
        charaToCN.put("Bennett", "班尼特");
        charaToCN.put("Chongyun", "重云");
        charaToCN.put("Diluc", "迪卢克");
        charaToCN.put("Diona", "迪奥娜");
        charaToCN.put("Eula", "优菈");
        charaToCN.put("Fischl", "菲谢尔");
        charaToCN.put("Ganyu", "甘雨");
        charaToCN.put("Gorou", "五郎");
        charaToCN.put("HuTao", "胡桃");
        charaToCN.put("Jean", "琴");
        charaToCN.put("KaedeharaKazuha", "枫原万叶");
        charaToCN.put("Kaeya", "凯亚");
        charaToCN.put("KamisatoAyaka", "神里绫华");
        charaToCN.put("Keqing", "刻晴");
        charaToCN.put("Klee", "可莉");
        charaToCN.put("KujouSara", "九条裟罗");
        charaToCN.put("Lisa", "丽莎");
        charaToCN.put("Mona", "莫娜");
        charaToCN.put("Ningguang", "凝光");
        charaToCN.put("Qiqi", "七七");
        charaToCN.put("RaidenShogun", "雷电将军");
        charaToCN.put("Razor", "雷泽");
        charaToCN.put("SangonomiyaKokomi", "珊瑚宫心海");
        charaToCN.put("Sayu", "早柚");
        charaToCN.put("Shenhe", "申鹤");
        charaToCN.put("Sucrose", "砂糖");
        charaToCN.put("Tartaglia", "达达利亚");
        charaToCN.put("Thoma", "托马");
        charaToCN.put("TravelerAnemo", "风主");
        charaToCN.put("TravelerElectro", "雷主");
        charaToCN.put("TravelerGeo", "岩主");
        charaToCN.put("Venti", "温迪");
        charaToCN.put("Xiangling", "香菱");
        charaToCN.put("Xiao", "魈");
        charaToCN.put("Xingqiu", "行秋");
        charaToCN.put("Xinyan", "辛焱");
        charaToCN.put("YaeMiko", "八重神子");
        charaToCN.put("Yanfei", "烟绯");
        charaToCN.put("Yoimiya", "宵宫");
        charaToCN.put("YunJin", "云堇");
        charaToCN.put("Zhongli", "钟离");


        CNToArtifact = reverseMap(artifactKeyToCN);
        CNToSlotKey = reverseMap(slotKeyToCN);
        CNToStatKey = reverseMap(statKeyToCN);
        CNToChara = reverseMap(charaToCN);

    }

    public static void main(String[] args) throws IOException {

//        List<String> charas1 = Arrays.asList("凝光");
//        List<String> charas2 = new ArrayList<>();


        //Danger
        int disposePercent = 25;

        //是否分解杯子等
        mode = MODE.All;

//
//        //检查名称输入
//        for (String chara : charas1) {
//            if (!CNToChara.containsKey(chara)) {
//                System.err.println(chara + "  输入有误");
//            } else {
//                charas2.add(CNToChara.get(chara));
//            }
//
//        }
//        final List<String> charas = charas2;
//        printLine();
//        System.out.println("额外设定的角色");
//        System.out.println(charas1);


        File goodFile = new File("C:\\Users\\lcy\\Desktop\\yas\\good.json");
//        File goodFile = new File("C:\\Users\\lcy\\Desktop\\yas\\goodlcy.json");
//        File analysisFile = new File("C:\\Users\\lcy\\Desktop\\analysis.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(goodFile)));
//        BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(analysisFile)));
        String rLine;
        StringBuilder builder = new StringBuilder();
        while ((rLine = br.readLine()) != null) {
            builder.append(rLine);
        }
//        String rLine2;
//        StringBuilder builder2 = new StringBuilder();
//        while ((rLine2 = br2.readLine()) != null) {
//            builder2.append(rLine2);
//        }
//
//        JSONObject analysisJson = JSONObject.parseObject(builder2.toString());
        JSONObject goodJson = JSONObject.parseObject(builder.toString());


        //用于获取index 不能修改
        oriAllArtifacts = goodJson.getJSONArray("artifacts");
        JSONArray artifacts = filterAndMake(oriAllArtifacts, j -> true);


        System.out.println("总计圣遗物数量：" + artifacts.size());

        //筛选五星
        filter(artifacts, j -> j.getInteger("rarity") >= 5);

        System.out.println("五星圣遗物数量：" + artifacts.size());

        int lockNum = sumAllI(artifacts, j -> j.getBoolean("lock") ? 1 : 0);
        System.out.println("已锁定的圣遗物数量：" + lockNum);
        System.out.println("未锁定的圣遗物数量：" + (artifacts.size() - lockNum));

        printLine();


        Map<String, Integer> artifactCountMap = new HashMap<>();
        Map<String, List<JSONObject>> allCharasArtifacts = new HashMap<>();

        for (int i = 0; i < artifacts.size(); i++) {
            JSONObject artifact = artifacts.getJSONObject(i);

            String setKey = artifactKeyToCN.get(artifact.getString("setKey"));
            String charaLocation = charaToCN.get(artifact.getString("location"));

            countByMap(artifactCountMap, setKey);
            if (charaLocation != null) {
                addByMap(allCharasArtifacts, charaLocation, artifact);
            }

        }


        System.out.println(artifactCountMap);
        printLine();
        System.out.println("已经装备的圣遗物（数量）");
        printMapAndNums(allCharasArtifacts);
        printLine();


//        System.out.println("设定模式：" + (mode == MODE.WhiteList ? "白名单" : "黑名单"));
//        if (mode == MODE.WhiteList) {
//            filter(allCharasArtifacts, list -> charas.contains(list.get(0).getString("location")));
//        } else if (mode == MODE.BlackList) {
//            filter(allCharasArtifacts, list -> !charas.contains(list.get(0).getString("location")));
//        }
//        System.out.println("过滤后的角色&圣遗物数量");
//        printMapAndNums(allCharasArtifacts);
//        printLine();
        System.out.println("角色的圣遗物词条数");


        for (String chara : allCharasArtifacts.keySet()) {
            List<JSONObject> mArtifacts = allCharasArtifacts.get(chara);
            System.out.print(chara + "=" + df.format(getArtifactFactor(mArtifacts)) + ", ");
        }

        //开始过滤

        printLine();
        //只处理0级的

        filter(artifacts, artifact -> artifact.getInteger("level") == 0);

        //如何定义垃圾圣遗物
        System.out.println("0级圣遗物的数量=" + artifacts.size());

        System.out.println("抛弃阈值： " + disposePercent + "%");

        JSONArray junk = filterAndMake(artifacts, artifact -> {


            if (mode == MODE.Restricted) {
                String slotKey = artifact.getString("slotKey");
                if (
                        slotKey.equals("sands")
                                || slotKey.equals("goblet")
                                || slotKey.equals("circlet")
                ) {
                    return false;
                }

            }

            String mainStatKey = artifact.getString("mainStatKey");
            if (mainStatKey.equals("electro_dmg_")
                    || mainStatKey.equals("pyro_dmg_")
                    || mainStatKey.equals("hydro_dmg_")
                    || mainStatKey.equals("cryo_dmg_")
                    || mainStatKey.equals("anemo_dmg_")
                    || mainStatKey.equals("geo_dmg_")
                    || mainStatKey.equals("physical_dmg_")
                    || mainStatKey.equals("eleMas")
                    || mainStatKey.equals("critRate_")
                    || mainStatKey.equals("critDMG_")
                    || mainStatKey.equals("enerRech_")
                    || mainStatKey.equals("heal_")
            ) return false;


            return true;
        });
        System.out.println("第一步处理  剩余数量=" + junk.size());
        int beforeDispose = junk.size();

        JSONArray junk1 = filterAndMake(junk, artifact -> {
            if (getArtifactFactor(artifact) == 0) return true;
            return false;
        });
        System.out.println("第二步处理  纯垃圾数量=" + junk1.size());

        junk.removeAll(junk1);


        //一般攻击类
        JSONArray junk2 = filterPercentAndMake(junk, a -> {
            String setKey = a.getString("setKey");
            if ((
                    setKey.equals("HeartOfDepth")
                            || setKey.equals("BlizzardStrayer")
                            || setKey.equals("GladiatorsFinale")
                            || setKey.equals("Lavawalker")
                            || setKey.equals("CrimsonWitchOfFlames")
                            || setKey.equals("Thundersoother")
                            || setKey.equals("ThunderingFury")
                            || setKey.equals("BloodstainedChivalry")
                            || setKey.equals("WanderersTroupe")
                            || setKey.equals("ShimenawasReminiscence")
                            || setKey.equals("VermillionHereafter")
                            || setKey.equals("EchoesOfAnOffering")
                            || setKey.equals("PaleFlame")
            ) && true) {
                return getArtifactFactor(a);
            } else {
                return -1d;
            }

        }, disposePercent);
        System.out.println("第三步处理    抛弃数量=" + junk2.size());

        junk.removeAll(junk2);

        Map<String, Double> geo_def_weight = new HashMap<>();
        geo_def_weight.put("critDMG_", 1d);
        geo_def_weight.put("critRate_", 1d);
        geo_def_weight.put("atk_", 0d);
        geo_def_weight.put("atk", 0d);
        geo_def_weight.put("hp_", 0.5d);
        geo_def_weight.put("hp", 0.2d);
        geo_def_weight.put("def_", 0.5d);
        geo_def_weight.put("def", 0.2d);
        geo_def_weight.put("enerRech_", 0.2d);
        geo_def_weight.put("eleMas", 0.2d);

        //岩
        JSONArray junk3 = filterPercentAndMake(junk, a -> {
            String setKey = a.getString("setKey");
            if ((
                    setKey.equals("ArchaicPetra")
                            || setKey.equals("RetracingBolide")
                            || setKey.equals("TenacityOfTheMillelith")
                            || setKey.equals("HuskOfOpulentDreams")
            ) && true) {
                return getArtifactFactor(a, geo_def_weight);
            } else {
                return -1d;
            }

        }, disposePercent);
        System.out.println("第四步处理  抛弃数量=" + junk3.size());
        junk.removeAll(junk3);

        Map<String, Double> enerRech_weight = new HashMap<>();
        enerRech_weight.put("critDMG_", 1d);
        enerRech_weight.put("critRate_", 1d);
        enerRech_weight.put("atk_", 0.5d);
        enerRech_weight.put("atk", 0d);
        enerRech_weight.put("hp_", 0.01d);
        enerRech_weight.put("hp", 0d);
        enerRech_weight.put("def_", 0.01d);
        enerRech_weight.put("def", 0d);
        enerRech_weight.put("enerRech_", 0.5d);
        enerRech_weight.put("eleMas", 0.5d);

        //充能
        JSONArray junk4 = filterPercentAndMake(junk, a -> {
            String setKey = a.getString("setKey");
            if ((
                    setKey.equals("EmblemOfSeveredFate")
                            || setKey.equals("NoblesseOblige")
            ) && true) {
                return getArtifactFactor(a, enerRech_weight);
            } else {
                return -1d;
            }

        }, disposePercent);
        System.out.println("第五步处理  抛弃数量=" + junk4.size());
        junk.removeAll(junk4);


        //治疗
        Map<String, Double> heal_weight = new HashMap<>();
        heal_weight.put("critDMG_", 0.01d);
        heal_weight.put("critRate_", 0.01d);
        heal_weight.put("atk_", 0.2d);
        heal_weight.put("atk", 0d);
        heal_weight.put("hp_", 1d);
        heal_weight.put("hp", 0.4d);
        heal_weight.put("def_", 0.01d);
        heal_weight.put("def", 0d);
        heal_weight.put("enerRech_", 1d);
        heal_weight.put("eleMas", 0.05d);

        //治疗
        JSONArray junk5 = filterPercentAndMake(junk, a -> {
            String setKey = a.getString("setKey");
            if ((
                    setKey.equals("OceanHuedClam")
                            || setKey.equals("MaidenBeloved")
            ) && true) {
                return getArtifactFactor(a, heal_weight);
            } else {
                return -1d;
            }

        }, disposePercent);
        System.out.println("第六步处理  抛弃数量=" + junk5.size());
        junk.removeAll(junk5);


        Map<String, Double> wind_weight = new HashMap<>();
        wind_weight.put("critDMG_", 1d);
        wind_weight.put("critRate_", 1d);
        wind_weight.put("atk_", 0.8d);
        wind_weight.put("atk", 0d);
        wind_weight.put("hp_", 0.01d);
        wind_weight.put("hp", 0d);
        wind_weight.put("def_", 0.01d);
        wind_weight.put("def", 0d);
        wind_weight.put("enerRech_", 0.5d);
        wind_weight.put("eleMas", 0.3d);

        //风伤
        JSONArray junk6 = filterPercentAndMake(junk, a -> {
            String setKey = a.getString("setKey");
            if ((
                    setKey.equals("ViridescentVenerer")
            ) && true) {
                return getArtifactFactor(a, wind_weight);
            } else {
                return -1d;
            }

        }, disposePercent);
        System.out.println("第七步处理  抛弃数量=" + junk6.size());
        junk.removeAll(junk6);

        //结束处理
        System.out.println();

        //开始上锁

        //junk就是剩下的有用的0级
        lockArtifact(junk);
        unlockArtifact(junk1);
        unlockArtifact(junk2);
        unlockArtifact(junk3);
        unlockArtifact(junk4);
        unlockArtifact(junk5);
        unlockArtifact(junk6);

        System.out.println("总抛弃数量：" + (beforeDispose - junk.size()));

        printLine();
        System.out.println("处理结束，切换锁数量为： " + lockSwitch.size());

        Collections.sort(lockSwitch);
        JSONArray lock = new JSONArray();
        lock.addAll(lockSwitch);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("C:\\Users\\lcy\\Desktop\\yas\\lock.json")));
        bw.write(lock.toString());
        bw.flush();
        bw.close();

        Scanner sc = new Scanner(System.in);
        System.out.println("按回车以显示抛弃详情");
        String s = sc.nextLine();

        for (Object o : junk1) {
            JSONObject j = (JSONObject) o;
            printArtifact(j, weight);
        }
        printLine();
        for (Object o : junk2) {
            JSONObject j = (JSONObject) o;
            printArtifact(j, weight);
        }
        printLine();
        for (Object o : junk3) {
            JSONObject j = (JSONObject) o;
            printArtifact(j, geo_def_weight);
        }
        printLine();
        for (Object o : junk4) {
            JSONObject j = (JSONObject) o;
            printArtifact(j, enerRech_weight);
        }
        printLine();
        for (Object o : junk5) {
            JSONObject j = (JSONObject) o;
            printArtifact(j, heal_weight);
        }
        printLine();
        for (Object o : junk6) {
            JSONObject j = (JSONObject) o;
            printArtifact(j, wind_weight);
        }

    }

    private static void printArtifact(JSONObject artifact, Map<String, Double> weight) {
        String setKey = artifact.getString("setKey");
        String slotKey = artifact.getString("slotKey");
        String level = artifact.getString("level");
        String mainStatKey = artifact.getString("mainStatKey");
        JSONArray substats = artifact.getJSONArray("substats");

        System.out.print(artifactKeyToCN.get(setKey) + "\t" +
                slotKeyToCN.get(slotKey) + "\t" +
                level + "级 主属性：" + statKeyToCN.get(mainStatKey) + " [ ");
        double factor = 0;
        for (Object substat : substats) {
            JSONObject sub = (JSONObject) substat;
            String key = sub.getString("key");
            double value = sub.getDouble("value");

            double subArtifactFactorNum = weight.get(key) * getSubArtifactFactorNum(key, value);
            factor += subArtifactFactorNum;
            System.out.print(statKeyToCN.get(key) + ", " +
                    df.format(value) + "(" + df.format(subArtifactFactorNum) + "), ");
        }

        System.out.println("总权重词条数：" + df.format(factor) + " ]");
    }

    private static void switchLockArtifact(JSONArray artifacts) {
        for (Object o : artifacts) {
            JSONObject artifact = (JSONObject) o;
            switchLockArtifact(artifact);
        }
    }

    private static void switchLockArtifact(JSONObject artifact) {
        artifact.put("lock", !artifact.getBooleanValue("lock"));
        int index = oriAllArtifacts.indexOf(artifact);
        if (!lockSwitch.contains(index)) {
            lockSwitch.add(index);
        } else {
            lockSwitch.remove((Integer) index);
        }

    }

    private static void lockArtifact(JSONArray artifacts) {
        for (Object o : artifacts) {
            JSONObject artifact = (JSONObject) o;
            lockArtifact(artifact);
        }
    }

    private static void lockArtifact(JSONObject artifact) {
        boolean lock = artifact.getBooleanValue("lock");
        if (lock) return;
        artifact.put("lock", true);
        int index = oriAllArtifacts.indexOf(artifact);
        if (!lockSwitch.contains(index)) {
            lockSwitch.add(index);
        } else {
            lockSwitch.remove((Integer) index);
        }

    }

    private static void unlockArtifact(JSONArray artifacts) {
        for (Object o : artifacts) {
            JSONObject artifact = (JSONObject) o;
            unlockArtifact(artifact);
        }
    }

    private static void unlockArtifact(JSONObject artifact) {
        boolean lock = artifact.getBooleanValue("lock");
        if (!lock) return;
        artifact.put("lock", false);
        int index = oriAllArtifacts.indexOf(artifact);
        if (!lockSwitch.contains(index)) {
            lockSwitch.add(index);
        } else {
            lockSwitch.remove((Integer) index);
        }

    }


    private static void printLine() {
        System.out.println();
        System.out.println("===================================");
    }

    public static <K> void countByMap(Map<K, Integer> map, K key) {
        map.putIfAbsent(key, 0);
        map.put(key, map.get(key) + 1);
    }

    public static <T> Map<T, T> reverseMap(Map<T, T> map) {
        Map<T, T> newMap = new HashMap<>();
        for (T t : map.keySet()) {
            newMap.put(map.get(t), t);
        }
        return newMap;
    }

    public static double getSubArtifactFactorNum(String key, double num) {
        return num / values.get(key);
    }


    public static double getArtifactFactor(JSONArray artifacts) {
        double sum = 0;
        for (Object o : artifacts) {
            JSONObject artifact = (JSONObject) o;
            sum += getArtifactFactor(artifact);
        }
        return sum;
    }

    public static double getArtifactFactor(List<JSONObject> artifacts) {
        double sum = 0;
        for (JSONObject artifact : artifacts) {
            sum += getArtifactFactor(artifact);
        }
        return sum;
    }

    public static double getArtifactFactor(JSONObject artifact) {
        JSONArray substats = artifact.getJSONArray("substats");
        double sum = 0;
        for (Object o1 : substats) {
            JSONObject sub = (JSONObject) o1;
            String key = sub.getString("key");
            double value = sub.getDoubleValue("value");
            double factor = getSubArtifactFactorNum(key, value);

            sum += factor * weight.get(key);
        }
        return sum;
    }

    public static double getArtifactFactor(JSONObject artifact, Map<String, Double> weight) {
        JSONArray substats = artifact.getJSONArray("substats");
        double sum = 0;
        for (Object o1 : substats) {
            JSONObject sub = (JSONObject) o1;
            String key = sub.getString("key");
            double value = sub.getDoubleValue("value");
            double factor = getSubArtifactFactorNum(key, value);

            sum += factor * weight.get(key);
        }
        return sum;
    }

    public static double getFactorExpectation(JSONObject artifactItem) {

        return 0;
    }


    private static void filter(JSONArray jsonArray, Predicate<JSONObject> p) {
        JSONArray jsonArray1 = new JSONArray();
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            if (!p.test(jsonObject)) jsonArray1.add(jsonObject);
        }
        jsonArray.removeAll(jsonArray1);
    }

    private static <T> void filter(Map<String, T> map, Predicate<T> p) {
        Set<String> set = new HashSet<>();
        for (String key : map.keySet()) {
            T t = map.get(key);
            if (!p.test(t)) set.add(key);
        }
        for (String s : set) {
            map.remove(s);
        }
    }

    private static <T> void printMapAndNums(Map<String, List<T>> map) {
        for (String key : map.keySet()) {
            System.out.print(key + "=" + map.get(key).size() + ", ");
        }

    }


    private static JSONArray filterAndMake(JSONArray jsonArray, Predicate<JSONObject> p) {
        JSONArray jsonArray1 = new JSONArray();
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            if (p.test(jsonObject)) jsonArray1.add(jsonObject);
        }
        return jsonArray1;
    }

    private static JSONArray filterPercentAndMake(JSONArray jsonArray, Function<JSONObject, Double> p, int percentJunk) {


        LinkedHashMap<JSONObject, Double> w = new LinkedHashMap<>();

        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            Double apply = p.apply(jsonObject);
            if (apply >= 0) {
                w.put(jsonObject, apply);
            }
        }
        System.out.println("↓ 总数=" + w.size());

        ArrayList<Map.Entry<JSONObject, Double>> list =
                new ArrayList<>(w.entrySet());
        JSONArray jsonArray1 = new JSONArray();

        list.sort((o1, o2) -> ((o1.getValue() - o2.getValue() == 0) ?
                0 : (o1.getValue() - o2.getValue() > 0) ? 1 : -1));

        for (int i = 0; i < list.size(); i++) {
            Map.Entry<JSONObject, Double> jsonObjectDoubleEntry = list.get(i);
            if (i < (double) percentJunk * list.size() / 100) {
                jsonArray1.add(jsonObjectDoubleEntry.getKey());
            }

        }


        return jsonArray1;
    }


    private static double sumAllD(JSONArray jsonArray, Function<JSONObject, Double> f) {
        double num = 0;
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            num += f.apply(jsonObject);
        }
        return num;
    }

    private static int sumAllI(JSONArray jsonArray, Function<JSONObject, Integer> f) {
        int num = 0;
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            num += f.apply(jsonObject);
        }
        return num;
    }

    private static <T> void addByMap(Map<String, List<T>> map, String key, T item) {
        map.putIfAbsent(key, new ArrayList<>());
        map.get(key).add(item);

    }


}


enum MODE {
    Restricted, All
}


