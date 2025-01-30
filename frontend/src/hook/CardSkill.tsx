import { useState } from "react";
import { useSpring, animated as a } from "@react-spring/web";
import { useInView } from "react-intersection-observer";
import styles from './styles.module.css'

interface ComponentProps {
  childrenBack: React.ReactNode;
  childrenFront: React.ReactNode;
}

const CardSkill: React.FC<ComponentProps> = ({ childrenBack, childrenFront }) => {
  const [flipped, setFlipped] = useState(false);
  
  // Detecta quando o componente entra na tela
  const { ref, inView } = useInView({ triggerOnce: true });

  // Animação do flip
  const { transform, opacity } = useSpring({
    opacity: flipped ? 1 : 0,
    transform: `perspective(600px) rotateX(${flipped ? 180 : 0}deg)`,
    config: { mass: 5, tension: 500, friction: 80 },
  });

  // Animação de balanço ao aparecer na tela
  const bounce = useSpring({
    from: { transform: "translateY(-10px)" },
    to: { transform: "translateY(0px)" },
    config: { tension: 300, friction: 10 },
    loop: inView ? { reverse: true } : false,
  });

  return (
    <a.div
      ref={ref}
      className={styles.container}
      style={inView ? bounce : {}}
      onClick={() => setFlipped((state) => !state)}
    >
      <a.div
        className={`${styles.c} ${styles.back}`}
        style={{ opacity: opacity.to((o) => 1 - o), transform }}
      >
        {childrenFront}
      </a.div>
      <a.div
        className={`${styles.c} ${styles.front}`}
        style={{
          opacity,
          transform,
          rotateX: "180deg",
        }}
      >
        {childrenBack}
      </a.div>
    </a.div>
  );
};

export default CardSkill;
