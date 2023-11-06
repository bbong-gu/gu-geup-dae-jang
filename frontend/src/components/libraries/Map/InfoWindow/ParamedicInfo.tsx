import { renderToString } from "react-dom/server";
import { Tmapv3 } from "../Map";
import InfoContents from "./InfoContents";
import { ParamedicInfoContainer } from "./ParamedicInfo.style";
import { useEffect, useState } from 'react';


function ParamedicInfo(props: any) {
    const [paraInfo, setParaInfo] = useState<any[]>([]);
    const updateInfo = () => {
        if (props.map !== undefined && props.paraRequestList !== undefined) {
            let info: any[] = []
            for (var i = 0; i < props.paraRequestList.length; i++) {
                var lonlat = new Tmapv3.LatLng(props.paraRequestList[i].latitude, props.paraRequestList[i].longitude);
                const infoWindow = new Tmapv3.InfoWindow({
                    position: lonlat = lonlat,
                    offset: new Tmapv3.Point(0, -30),
                    content: renderToString(
                        <InfoContents
                            id={props.paraRequestList[i].id}
                            ktas={props.paraRequestList[i].ktas}
                            addr={props.paraRequestList[i].address}
                            requestAt={props.paraRequestList[i].createdAt}
                            elapseMin={"-"}
                            leftTime={props.paraRequestList[i].duration}
                        />
                    ),
                    border: '0px solid #ffffff',
                    type: 2,
                    map: props.map
                });
                info.push(infoWindow);
            }
            // console.log(info)
            setParaInfo(info);
        }
    }
    const deleteInfo = () => {
        console.log(paraInfo)
        for (let i = 0; i < paraInfo.length; i++) {
            paraInfo[i].setMap(null);
            // console.log()
        }
        setParaInfo([]);
    }
    useEffect(() => {
        if (props.map !== undefined && props.paraRequestList !== undefined) {
            deleteInfo();
            if(paraInfo.length==0)updateInfo();
        }
    }, [props]);

    return (
        <ParamedicInfoContainer>
        </ParamedicInfoContainer>
    );
}

export default ParamedicInfo;