import { useEffect, ReactNode } from 'react';
import { useLocation } from 'react-router-dom';

function ScrollToTop({ children }: { children: ReactNode }) {
    const { pathname } = useLocation();

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [pathname]);

    return <>{children}</>;
}

export default ScrollToTop;
