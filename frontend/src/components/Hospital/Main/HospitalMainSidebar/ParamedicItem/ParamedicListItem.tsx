import { ParamedicItemContainer, ParamedicItemContent, ItemRequestAt, ItemParaType, ItemParaInfo, ItemParaTagGroup } from "./ParamedicListItem.style";
import { BtnToggle } from "/src/components/Commons/Atoms/Button";
import { DivKtasInfo, DivTag } from "/src/components/Commons/Atoms/Div";
import { ParamedicItem } from "/src/components/libraries/Map/Map";
import theme from "/src/styles";


const ParamedicListItem = (props: any) => {

  return (
    <ParamedicItemContainer>
      <ParamedicItemContent onClick={props.onclick}>
        <DivKtasInfo
          $position="absolute"
          $right="0%"
          $top="0%"
          $ktas={props.ktas}
          $width="50px"
          $height="25px"
          $borderRadius="0px 0px 0px 10px"
          $fontSize={theme.font.Small5_12}>
          KTAS1
        </DivKtasInfo>
        <ItemRequestAt>{props.requestAt}</ItemRequestAt>
        <ItemParaType>{props.paraType}</ItemParaType>
        <ItemParaInfo>{props.paraInfo}</ItemParaInfo>
        <ItemParaTagGroup>
          {props.paraTag.map((item: string, index: number) => (
            <DivTag
              key={index}
              $margin="2px 5px 50px 2px"
              $width="fit-content"
              $height="18px"
              $borderRadius="5px"
              $textAlign="center"
              $padding="2px"
              $fontSize={theme.font.Small5_12}
            >{item}</DivTag>
          ))}
        </ItemParaTagGroup>


        <BtnToggle
          $width="50%"
          $height="30px"
          $position="absolute"
          $left="0%"
          $bottom="0%"
          $borderRadius="0px"
          $color={theme.color.pinkDrak}
          $fontSize={theme.font.Small1_16}
        >
          거절
        </BtnToggle>

        <BtnToggle
          $width="50%"
          $height="30px"
          $position="absolute"
          $right="0%"
          $bottom="0%"
          $borderRadius="0px"
          $color={theme.color.white}
          $fontSize={theme.font.Small1_16}
          $backgroundColor={theme.color.pinkDrak}>
          승인
        </BtnToggle>
      </ParamedicItemContent>
    </ParamedicItemContainer>
  );
};

export default ParamedicListItem;
