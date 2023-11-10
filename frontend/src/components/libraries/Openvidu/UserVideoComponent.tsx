import { useRecoilState } from "recoil";
import { VedioOuterDiv } from "./UserVideoComponent.style";
import { useEffect, useRef, useState } from "react";
import OpenViduVideoComponent from "./OvVideo";

const UserVideoComponent = (props: any) => {
  const anchorRef = useRef<HTMLDivElement>(null);
  return (
    <VedioOuterDiv
      className="streamcomponent"
      ref={anchorRef}
      style={{ fontSize: anchorRef.current?.offsetWidth }}
    >
      {props.streamManager !== undefined ? (
        <OpenViduVideoComponent streamManager={props.streamManager} />
      ) : null}
    </VedioOuterDiv>
  );
};

export default UserVideoComponent;
