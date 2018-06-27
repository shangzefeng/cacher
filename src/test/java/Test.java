/*
 * Copyright (c) 2018, net.fqj
 *
 * All rights reserved.
 */
import com.caucho.hessian.io.Deflation;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.shusi.commons.model.ServiceResult;
import org.apache.commons.lang.StringUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Fsz
 */
public class Test {

    public static void main(String[] args) {
        try {
            final ServiceResult result = new ServiceResult();
            result.setAdditionalProperties("xx", "yy");
            result.setMsg("1234567");
            ser1(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ssss() {
        final Map<String, Integer> map = new HashMap<>();

        String spel = "a:{2}-bddd{1}";
        xx(spel);
        String[] mm = StringUtils.split(spel, "{", 2);
        spel = mm[1];
        String[] nn = StringUtils.split(spel, "}", 2);
        map.put(mm[0], Integer.valueOf(nn[0]));
        spel = nn[1];

        mm = StringUtils.split(spel, "{", 2);
        spel = mm[1];
        nn = StringUtils.split(spel, "}", 2);
        map.put(mm[0], Integer.valueOf(nn[0]));
        spel = nn[1];

        System.out.println("");
    }

    public static List<Map<String, Integer>> xx(String spel) {
        final List<Map<String, Integer>> list = new ArrayList<>();
        while (!StringUtils.isBlank(spel)) {

            final Map<String, Integer> map = new HashMap<>();
            list.add(map);

            String[] prefix = StringUtils.split(spel, "{", 2);
            if (prefix.length == 1) {
                map.put(spel, -1);
                break;
            }
            spel = prefix[1];

            String[] suffix = StringUtils.split(spel, "}", 2);

            map.put(prefix[0], Integer.valueOf(suffix[0]));

            spel = suffix.length == 1 ? "" : suffix[1];
        }
        return list;
    }

    public static void ser(final ServiceResult result) throws IOException {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output hessian2Output = new Hessian2Output(baos);
        final Deflation envelope = new Deflation();
        hessian2Output = envelope.wrap(hessian2Output);
        hessian2Output.writeObject(result);
        hessian2Output.flush();
        hessian2Output.close();

        final byte[] by = baos.toByteArray();

        final ByteArrayInputStream bais = new ByteArrayInputStream(by);

        Hessian2Input hip = new Hessian2Input(bais);
        hip = envelope.unwrap(hip);
        final ServiceResult xxxx = (ServiceResult) hip.readObject(ServiceResult.class);
        System.out.println(xxxx.getMsg());
        hip.close();
    }

    public static void ser1(final ServiceResult result) throws IOException {

        ByteArrayOutputStream baos = null;
        Hessian2Output hessian2Output = null;
        try {
            baos = new ByteArrayOutputStream();
            hessian2Output = new Hessian2Output(baos);
            final Deflation deflation = new Deflation();

            hessian2Output = deflation.wrap(hessian2Output);
            hessian2Output.writeObject(result);
            hessian2Output.flush();
            hessian2Output.close();

            final byte[] by = baos.toByteArray();
            System.out.println(by.length);
        } catch (Exception e) {
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
            } catch (Exception e) {
            }
            try {
                if (null != hessian2Output) {
                    hessian2Output.close();
                }
            } catch (Exception e) {
            }
        }
    }
}
