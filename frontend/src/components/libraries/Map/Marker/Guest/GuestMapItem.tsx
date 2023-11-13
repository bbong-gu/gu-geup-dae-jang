import { useEffect, useState } from "react";
import { deleteMarker } from "/src/constants/function";
import { Tmapv3 } from "../../Map";

function GuestMapItem(props: any) {


    const updateHospitalMarkers = () => {
      if (props.map !== undefined && props.hosList !== undefined) {
          for (var i = 0; i < props.hosList.length; i++) {
              var lonlat = new Tmapv3.LatLng(props.hosList[i].pos.lat, props.hosList[i].pos.lon);
              // var title = props.hosList[i].name;
              // let color
              // if(props.hosList[i].response !== undefined){
              //     color = props.hosList[i].response === "accept" ? "#37b829" : props.hosList[i].response === "refuse" ? "#ff1500" : "#f6f157"
              // }
              // else{
              //     color = "#F66457"
              // }
              const size = new Tmapv3.Size(30, 30);
              const marker = new Tmapv3.Marker({
                  position: lonlat,
                  draggable: true,
                  map: props.map,
                  // color: color,
                  iconSize: size,
                  icon: "/src/assets/hospital/map-marker-hospital.png",
                  // label: title //Marker의 라벨.
              })
              marker.name = props.hosList[i].id
              
              // 마커 이벤트 등록용
              // const tmp = props.hosList[i]
              // marker.on("Click", () => {
              //     setParaRequsetItem(tmp)
              // });
          }
      }
  }
    const updateGuestMarkers = () =>{
      if (props.map !== undefined) {
        deleteMarker(2);
        const lonlat = new Tmapv3.LatLng(props.pos.lat, props.pos.lon);
        const size = new Tmapv3.Size(30, 30);
        const marker = new Tmapv3.Marker({
          position: lonlat,
          map: props.map,
          iconSize: size,
          icon: "/src/assets/share/map-marker-my.png",
          // label: "asdasd" //Marker의 라벨.
          // color: "#b52626",
        });
        updateHospitalMarkers();
      }
    }

    useEffect(() => {
      updateGuestMarkers()
      const intervalId = setInterval(() => {
        updateGuestMarkers()}, 10000)
      return () => clearInterval(intervalId);
    }, [props.hosList]);

  return <></>;
}

export default GuestMapItem;
