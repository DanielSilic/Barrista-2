import BottomMenuBar from './BottomBar.tsx';
import { ReactNode } from 'react';

type MainLayoutProps = {
    children?: ReactNode;
};
function MainLayout({ children }: MainLayoutProps) {
    return (
        <div className="main-layout">
            <div className="content">{children}</div>
            <BottomMenuBar />
        </div>
    );
}

export default MainLayout;

