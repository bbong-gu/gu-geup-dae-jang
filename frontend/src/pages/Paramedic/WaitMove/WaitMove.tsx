import { useState } from 'react';
import { useRecoilValue } from 'recoil';
import { showWaitState } from '/src/recoils/ParamedicAtoms';
import * as S from './WaitMove.style';
import M from '/src/components/Commons/Molecules';
import { ParamedicMap, Toggle, Times, Wait, Move } from '/src/components/Paramedic/WaitMove';
import { MapProps } from '/src/types/map';

function WaitMove() {
  const [mapProps, setMapProps] = useState<MapProps>();
  const showWait = useRecoilValue(showWaitState);

  return (
    <S.Container>
      <S.Wrapper>
        <M.ParamedicHeader />
        <ParamedicMap mapProps={mapProps} setMapProps={setMapProps} />
        <S.ContentBox>
          <Toggle />
          <Times />
          {showWait ? <Wait /> : <Move />}
        </S.ContentBox>
      </S.Wrapper>
    </S.Container>
  );
}

export default WaitMove;
